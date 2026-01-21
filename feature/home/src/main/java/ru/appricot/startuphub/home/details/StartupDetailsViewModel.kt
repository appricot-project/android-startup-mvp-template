package ru.appricot.startuphub.home.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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

@HiltViewModel(assistedFactory = StartupDetailsViewModel.Factory::class)
class StartupDetailsViewModel @AssistedInject constructor(@Assisted val id: String, private val repository: StartupRepository) :
    ViewModel() {

    private val _errors = MutableSharedFlow<StartupUiState.Error>()
    val errors = _errors.asSharedFlow()
    val state: StateFlow<StartupUiState> =
        flow<StartupUiState> {
            val result = repository.getStartup(id)
            emit(StartupUiState.Data(result))
        }
            .catch {
                val error = StartupUiState.Error(it)
                emit(error)
                viewModelScope.launch {
                    _errors.emit(error)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StartupUiState.Loading,
            )

    @AssistedFactory
    interface Factory {
        fun create(id: String): StartupDetailsViewModel
    }
}
