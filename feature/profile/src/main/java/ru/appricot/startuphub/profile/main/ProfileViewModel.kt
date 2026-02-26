package ru.appricot.startuphub.profile.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.appricot.startuphub.auth.domain.GetUserProfile
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUserProfile: GetUserProfile) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _errors = MutableSharedFlow<ProfileUiState.Error>()
    val errors = _errors.asSharedFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { ProfileUiState.Loading }
            getUserProfile()
                .onSuccess { result ->
                    _uiState.update {
                        ProfileUiState.Data(result)
                    }
                }
                .onFailure { failure ->
                    val error = ProfileUiState.Error(failure)
                    _uiState.update { error }
                    _errors.emit(error)
                }
        }
    }
}
