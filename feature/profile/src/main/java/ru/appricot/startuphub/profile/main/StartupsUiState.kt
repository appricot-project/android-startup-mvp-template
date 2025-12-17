package ru.appricot.startuphub.profile.main

sealed interface StartupsUiState {

    data object Loading : StartupsUiState
    data class Data(val text: String) : StartupsUiState
}
