package ru.appricot.startuphub.auth.signin

import android.content.Intent
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.appricot.startuphub.auth.domain.AuthorizationUseCase
import ru.appricot.startuphub.auth.signin.validation.EmailRules
import ru.appricot.startuphub.auth.signin.validation.Validator
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase,
    private val authorizationManager: AuthorizationManager,
) : ViewModel() {
    private val _errors = MutableSharedFlow<SignInUiState.Error>()
    val errors = _errors.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _emailValidationError = MutableStateFlow<Int?>(null)
    val emailValidationError: StateFlow<Int?> = _emailValidationError.asStateFlow()

    private val _state = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val state: StateFlow<SignInUiState> = _state.asStateFlow()

    private val emailValidator = Validator<String>(
        EmailRules.notEmpty,
        EmailRules.validFormat,
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
                .debounce(300) // Wait 300ms after user stops typing
                .distinctUntilChanged()
                .collect { email ->
                    validateEmail(email, fromUserInput = true)
                }
        }
    }

    private fun validateEmail(email: String, fromUserInput: Boolean = false) {
        val error = emailValidator.validate(email)
        _emailValidationError.update { error }
    }

    fun updateEmail(newEmail: String) {
        _email.update { newEmail }
    }

    fun signIn(onSuccess: (String) -> Unit) {
        val currentEmail = _email.value

        // Validate email immediately on sign in attempt
        validateEmail(currentEmail, fromUserInput = false)

        // Check if there's a validation error
        if (_emailValidationError.value != null) {
            return
        }

        viewModelScope.launch {
            _state.update { SignInUiState.Loading }

            flow {
                authorizationUseCase(currentEmail)
                    .onSuccess {
                        emit(SignInUiState.Success)
                        onSuccess(currentEmail)
                    }
                    .onFailure {
                        val errorState = SignInUiState.Error(it)
                        emit(errorState)
                        _errors.emit(errorState)
                    }
            }
                .collect { newState ->
                    _state.update { newState }
                }
        }
    }

    private val _authIntent = MutableSharedFlow<Intent>()
    val authIntent = _authIntent.asSharedFlow()

    fun onNextClick() {
        viewModelScope.launch {
            _authIntent.emit(authorizationManager.provideAuthorizationIntent())
        }
    }

    fun authResult(data: Intent?) {
        authorizationManager.handleAuthResponseIntent(data) { result ->
            result
                .onSuccess { state -> }
                .onFailure { error -> }
        }
    }

    override fun onCleared() {
        super.onCleared()
        authorizationManager.dispose()
    }
}
