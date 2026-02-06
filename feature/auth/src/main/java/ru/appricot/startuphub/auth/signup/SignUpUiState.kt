package ru.appricot.startuphub.auth.signup

import ru.appricot.startuphub.ui.ErrorLabel

sealed interface SignUpUiState {
    data object Idle : SignUpUiState
    data object Loading : SignUpUiState
    data object Registered : SignUpUiState
    data class Error(val throwable: Throwable) :
        SignUpUiState,
        ErrorLabel {
        override val message: String?
            get() = throwable.localizedMessage
    }
}
