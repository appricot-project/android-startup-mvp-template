package ru.appricot.startuphub.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.appricot.startuphub.startups.repository.StartupRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: StartupRepository) : ViewModel() {
    private val _errors = MutableSharedFlow<StartupsUiState.Error>()
    val errors = _errors.asSharedFlow()
    val state: StateFlow<StartupsUiState> =
        flow<StartupsUiState> {
            val result = repository.getStartups()
            emit(StartupsUiState.Data(result))
        }
            .catch {
                val error = StartupsUiState.Error(it)
                emit(error)
                viewModelScope.launch {
                    _errors.emit(error)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StartupsUiState.Loading,
            )
}
