package ru.appricot.startuphub.home.main

import ru.appricot.startuphub.startups.model.StartupModel

sealed interface StartupsUiState {

    data object Loading : StartupsUiState
    data class Data(val list: List<StartupModel>) : StartupsUiState
}
