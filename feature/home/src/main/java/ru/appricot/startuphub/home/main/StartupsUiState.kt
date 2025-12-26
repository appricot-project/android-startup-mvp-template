package ru.appricot.startuphub.home.main

import ru.appricot.startuphub.startups.model.StartupModel
import ru.appricot.startuphub.ui.ErrorLabel

sealed interface StartupsUiState {

    data object Loading : StartupsUiState
    data class Data(val list: List<StartupModel>) : StartupsUiState

    data class Error(val throwable: Throwable) :
        StartupsUiState,
        ErrorLabel {
        override val message: String?
            get() = throwable.localizedMessage
    }
}
