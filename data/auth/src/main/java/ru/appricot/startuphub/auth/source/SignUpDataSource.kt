package ru.appricot.startuphub.auth.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.appricot.startuphub.auth.AuthApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpDataSource @Inject constructor(private val authApi: AuthApi) {
    suspend fun signUp(email: String?, firstName: String?, lastName: String?): Boolean = withContext(Dispatchers.IO) {
        val request = SignUpRequest(email, firstName, lastName)
        authApi.register(request)
    }
}

data class SignUpRequest(val email: String?, val firstName: String?, val lastName: String?)
