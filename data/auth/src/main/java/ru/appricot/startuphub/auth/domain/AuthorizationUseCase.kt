package ru.appricot.startuphub.auth.domain

import jakarta.inject.Inject
import jakarta.inject.Singleton
import ru.appricot.startuphub.auth.repository.AuthRepository

@Singleton
class AuthorizationUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String?): Result<Unit> = try {
        val result = repository.signIn(email)
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
