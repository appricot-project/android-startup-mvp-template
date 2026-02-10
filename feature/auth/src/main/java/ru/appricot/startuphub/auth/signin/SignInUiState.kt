package ru.appricot.startuphub.auth.signin

import net.openid.appauth.AuthState
import ru.appricot.startuphub.ui.ErrorLabel

sealed interface SignInUiState {
    data object Idle : SignInUiState
    data object Loading : SignInUiState
    data class Success(val authState: AuthState) : SignInUiState
    data class Error(val throwable: Throwable) :
        SignInUiState,
        ErrorLabel {
        override val message: String?
            get() = throwable.localizedMessage
    }
}
