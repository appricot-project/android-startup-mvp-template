package ru.appricot.startuphub.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.appricot.startuphub.startups.repository.StartupRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: StartupRepository) : ViewModel() {
    private val _errors = MutableSharedFlow<StartupsUiState.Error>()
    val errors = _errors.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

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
            .combine(_searchQuery) { state, query ->
                when (state) {
                    is StartupsUiState.Data -> {
                        val filteredList = if (query.length >= 3) {
                            state.list.filter { startup ->
                                startup.name?.contains(query, ignoreCase = true) == true ||
                                    startup.category?.contains(query, ignoreCase = true) == true ||
                                    startup.city?.contains(query, ignoreCase = true) == true
                            }
                        } else {
                            state.list
                        }
                        state.copy(searchQuery = query, filteredList = filteredList)
                    }

                    else -> state
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StartupsUiState.Loading,
            )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
