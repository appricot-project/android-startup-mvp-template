package ru.appricot.startuphub.home

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import ru.appricot.navigation.EntryProviderInstaller
import ru.appricot.startuphub.home.details.StartupDetailsScreen
import ru.appricot.startuphub.home.details.StartupDetailsViewModel
import ru.appricot.startuphub.home.main.HomeScreen
import ru.appricot.startuphub.homeapi.Home
import ru.appricot.startuphub.homeapi.StartupDetails

@Module
@InstallIn(ActivityRetainedComponent::class)
class HomeModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = { navigator ->
        entry<Home> {
            HomeScreen(
                onDetailsClick = { navigator.navigate(StartupDetails(it)) }
            )
        }
        entry<StartupDetails> { key ->
            StartupDetailsScreen(
                viewModel = hiltViewModel<StartupDetailsViewModel, StartupDetailsViewModel.Factory>(
                    key = key.id.toString()
                ) {
                    it.create(key.id)
                },
                onBackClick = { navigator.goBack() }
            )
        }
    }
}
