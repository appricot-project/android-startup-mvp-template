package ru.appricot.startuphub.auth.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSource @Inject constructor() {
    suspend fun signIn(email: String?): Unit = withContext(Dispatchers.IO) {
        delay(1000)
    }
}
