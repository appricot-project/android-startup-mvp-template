package ru.appricot.startuphub.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.appricot.startuphub.startups.repository.StartupRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: StartupRepository) : ViewModel() {
    val state: StateFlow<StartupsUiState> =
        flow {
            emit(
                StartupsUiState.Data(
                    repository.getStartups(),
                ),
            )
        }
            .catch {
                Timber.d(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StartupsUiState.Loading,
            )
}
