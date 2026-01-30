package ru.appricot.startuphub.auth.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.appricot.startuphub.auth.signin.SignInUiState
import javax.inject.Inject

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<ConfirmationUiState>(ConfirmationUiState.Idle)
    val state: StateFlow<ConfirmationUiState> = _state.asStateFlow()

    private val _errors = MutableSharedFlow<SignInUiState.Error>()
    val errors = _errors.asSharedFlow()

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code.asStateFlow()

    private val _confirmationEvent = MutableSharedFlow<ConfirmEvent>()
    val confirmationEvent = _confirmationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _code
                .onEach {
                    if (it.length == 6) onConfirmClick()
                }
                .collect()
        }
    }

    fun onCodeChanged(code: String) {
        _code.update { code }
    }

    fun onConfirmClick() {
        val currentCode = _code.value
        if (currentCode.length < 6) {
            return
        }
        if (currentCode.length == 6) {
            _state.update { ConfirmationUiState.Loading }

            viewModelScope.launch {
                delay(2_000L)
                _state.update { ConfirmationUiState.Success }
                _confirmationEvent.emit(ConfirmEvent)
            }
        }
    }
}

data object ConfirmEvent
