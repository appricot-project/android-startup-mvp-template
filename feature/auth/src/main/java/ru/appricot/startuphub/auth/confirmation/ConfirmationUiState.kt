package ru.appricot.startuphub.auth.confirmation

import ru.appricot.startuphub.ui.ErrorLabel

sealed interface ConfirmationUiState {
    data object Idle : ConfirmationUiState
    data object Loading : ConfirmationUiState
    data object Success : ConfirmationUiState
    data class Error(val throwable: Throwable) :
        ConfirmationUiState,
        ErrorLabel {
        override val message: String?
            get() = throwable.localizedMessage
    }
}
