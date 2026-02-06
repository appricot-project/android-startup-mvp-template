package ru.appricot.startuphub.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.appricot.startuphub.auth.domain.SignUpUseCase
import ru.appricot.startuphub.auth.signup.validation.NameRules
import ru.appricot.startuphub.auth.signup.validation.SignUpEmailRules
import ru.appricot.startuphub.auth.signup.validation.Validator
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _errors = MutableSharedFlow<SignUpUiState.Error>()
    val errors = _errors.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _emailValidationError = MutableStateFlow<Int?>(null)
    val emailValidationError: StateFlow<Int?> = _emailValidationError.asStateFlow()

    private val _firstNameValidationError = MutableStateFlow<Int?>(null)
    val firstNameValidationError: StateFlow<Int?> = _firstNameValidationError.asStateFlow()

    private val _lastNameValidationError = MutableStateFlow<Int?>(null)
    val lastNameValidationError: StateFlow<Int?> = _lastNameValidationError.asStateFlow()

    private val _state = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val state: StateFlow<SignUpUiState> = _state.asStateFlow()

    private val emailValidator = Validator<String>(
        SignUpEmailRules.notEmpty,
        SignUpEmailRules.validFormat,
    )

    private val firstNameValidator = Validator<String>(
        NameRules.notEmpty,
        NameRules.minLength,
        NameRules.validFormat,
    )

    private val lastNameValidator = Validator<String>(
        NameRules.notEmpty,
        NameRules.minLength,
        NameRules.validFormat,
    )

    init {
        viewModelScope.launch {
            _email.debounce(1_000)
                .filter { it.isNotBlank() }
                .onEach { setupEmailValidation() }
                .first()
        }
    }

    private fun setupEmailValidation() {
        viewModelScope.launch {
            _email
                .debounce(300)
                .distinctUntilChanged()
                .collect { email ->
                    validateEmail(email, fromUserInput = true)
                }
        }
    }

    private fun setupFirstNameValidation() {
        viewModelScope.launch {
            _firstName
                .debounce(300)
                .distinctUntilChanged()
                .collect { firstName ->
                    validateFirstName(firstName, fromUserInput = true)
                }
        }
    }

    private fun setupLastNameValidation() {
        viewModelScope.launch {
            _lastName
                .debounce(300)
                .distinctUntilChanged()
                .collect { lastName ->
                    validateLastName(lastName, fromUserInput = true)
                }
        }
    }

    private fun validateEmail(email: String, fromUserInput: Boolean = false) {
        val error = emailValidator.validate(email)
        _emailValidationError.update { error }
    }

    private fun validateFirstName(firstName: String, fromUserInput: Boolean = false) {
        val error = firstNameValidator.validate(firstName)
        _firstNameValidationError.update { error }
    }

    private fun validateLastName(lastName: String, fromUserInput: Boolean = false) {
        val error = lastNameValidator.validate(lastName)
        _lastNameValidationError.update { error }
    }

    fun updateEmail(newEmail: String) {
        _email.update { newEmail }
    }

    fun updateFirstName(newFirstName: String) {
        _firstName.update { newFirstName }
    }

    fun updateLastName(newLastName: String) {
        _lastName.update { newLastName }
    }

    fun signUp() {
        val currentEmail = _email.value
        val currentFirstName = _firstName.value
        val currentLastName = _lastName.value

        // Validate all fields immediately on sign up attempt
        validateEmail(currentEmail, fromUserInput = false)
        validateFirstName(currentFirstName, fromUserInput = false)
        validateLastName(currentLastName, fromUserInput = false)

        // Check if there are any validation errors
        if (_emailValidationError.value != null ||
            _firstNameValidationError.value != null ||
            _lastNameValidationError.value != null
        ) {
            return
        }

        viewModelScope.launch {
            _state.update { SignUpUiState.Loading }
            signUpUseCase(currentEmail, currentFirstName, currentLastName)
                .onSuccess {
                    _state.update { SignUpUiState.Registered }
                }
                .onFailure {
                    val errorState = SignUpUiState.Error(it)
                    _state.update { errorState }
                    _errors.emit(errorState)
                }
        }
    }
}
