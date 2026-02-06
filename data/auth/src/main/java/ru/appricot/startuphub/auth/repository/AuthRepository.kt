package ru.appricot.startuphub.auth.repository

import ru.appricot.startuphub.auth.source.AuthDataSource
import ru.appricot.startuphub.auth.source.SignUpDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authDataSource: AuthDataSource, private val signupDataSource: SignUpDataSource) {
    suspend fun signIn(email: String?): Unit = authDataSource.signIn(email)
    suspend fun signUp(email: String?, lastName: String?, firstName: String?): Boolean = signupDataSource.signUp(email, firstName, lastName)
}
