package ru.appricot.startuphub.auth.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.appricot.startuphub.auth.AuthStateManager
import ru.appricot.startuphub.auth.model.UserProfile
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val authStateManager: AuthStateManager,
    private val userProfileFactory: UserProfile.Factory,
) {
    suspend fun get(): UserProfile? = withContext(Dispatchers.Default) {
        authStateManager.getCurrent().idToken?.let {
            userProfileFactory.create(it)
        }
    }
}
