package ru.appricot.startuphub.auth.repository

import ru.appricot.startuphub.auth.source.AuthDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authDataSource: AuthDataSource) {
    suspend fun signIn(email: String?): Unit = authDataSource.signIn(email)
}
