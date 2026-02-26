package ru.appricot.startuphub.auth.domain

import jakarta.inject.Inject
import ru.appricot.startuphub.auth.model.UserProfile
import ru.appricot.startuphub.auth.repository.ProfileRepository

class GetUserProfile @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(): Result<UserProfile?> = try {
        val result = repository.get()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
