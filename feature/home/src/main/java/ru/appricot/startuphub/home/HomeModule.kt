package ru.appricot.startuphub.home

import android.content.Context
import android.content.Intent
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.multibindings.IntoSet
import ru.appricot.navigation.EntryProviderInstaller
import ru.appricot.startuphub.home.details.StartupDetailsScreen
import ru.appricot.startuphub.home.details.StartupDetailsViewModel
import ru.appricot.startuphub.home.main.HomeScreen
import ru.appricot.startuphub.homeapi.Home
import ru.appricot.startuphub.homeapi.StartupDetails

@Module
@InstallIn(ActivityComponent::class)
class HomeModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(@ActivityContext context: Context): EntryProviderInstaller = { navigator ->
        entry<Home> {
            HomeScreen(
                onDetailsClick = { navigator.navigate(StartupDetails(it)) },
            )
        }
        entry<StartupDetails> { key ->
            StartupDetailsScreen(
                viewModel = hiltViewModel<StartupDetailsViewModel, StartupDetailsViewModel.Factory>(
                    key = key.id,
                ) {
                    it.create(key.id)
                },
                onShareClick = { details ->
                    context.startActivity(
                        Intent.createChooser(
                            Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_TEXT, details.description)
                                putExtra(Intent.EXTRA_SUBJECT, details.name)
                                type = "text/plain"
                            },
                            null,
                        ),
                    )
                },
                onBackClick = {
                    navigator.goBack()
                },
            )
        }
    }
}
