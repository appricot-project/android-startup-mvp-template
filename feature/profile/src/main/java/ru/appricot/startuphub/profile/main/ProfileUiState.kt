package ru.appricot.startuphub.profile.main

import ru.appricot.startuphub.auth.model.UserProfile

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Data(val profile: UserProfile?) : ProfileUiState
    data class Error(val throwable: Throwable) : ProfileUiState
}
