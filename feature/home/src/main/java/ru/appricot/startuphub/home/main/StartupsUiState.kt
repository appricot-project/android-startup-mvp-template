package ru.appricot.startuphub.home.main

sealed interface StartupsUiState {

    data object Loading : StartupsUiState
    data class Data(val list: List<StartupModel>) : StartupsUiState
}
