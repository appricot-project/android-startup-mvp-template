package ru.appricot.startuphub.profile

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import ru.appricot.navigation.EntryProviderInstaller
import ru.appricot.navigation.Profile
import ru.appricot.startuphub.homeapi.Home
import ru.appricot.startuphub.profile.main.ProfileScreen

@Module
@InstallIn(ActivityRetainedComponent::class)
class ProfileModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = { navigator ->
        entry<Profile> {
            ProfileScreen(
                onHomeClick = { navigator.navigate(Home) },
            )
        }
    }
}
