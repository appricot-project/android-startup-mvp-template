package ru.appricot.startuphub.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    companion object {
        private const val PREFS_NAME = "ru.appricot.startuphub.datastore.preferences"
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFS_NAME)

    @Provides
    @Singleton
    fun providesDS(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore
}
