package ru.appricot.startuphub.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {
    @Provides
    @Singleton
    fun providesAuthApi(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): AuthApi =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_AUTH_URL)
            .addConverterFactory(converterFactory).client(okHttpClient).build()
            .create(AuthApi::class.java)
}
