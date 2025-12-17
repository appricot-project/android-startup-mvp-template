package ru.appricot.startuphub

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {

/*    @Provides
    @ActivityRetainedScoped
    fun provideNavigator() : Navigator = Navigator(startDestination = Home)*/
}
