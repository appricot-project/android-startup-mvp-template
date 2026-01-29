package ru.appricot.startuphub.home.details

import ru.appricot.startuphub.startups.model.StartupDetailsModel
import ru.appricot.startuphub.ui.ErrorLabel

sealed interface StartupUiState {

    data object Loading : StartupUiState
    data class Data(val value: StartupDetailsModel?) : StartupUiState

    data class Error(val throwable: Throwable) :
        StartupUiState,
        ErrorLabel {
        override val message: String?
            get() = throwable.localizedMessage
    }
}
