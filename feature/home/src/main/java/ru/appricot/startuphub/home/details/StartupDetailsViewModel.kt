package ru.appricot.startuphub.home.details

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = StartupDetailsViewModel.Factory::class)
class StartupDetailsViewModel @AssistedInject constructor(@Assisted val id: Int) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): StartupDetailsViewModel
    }
}
