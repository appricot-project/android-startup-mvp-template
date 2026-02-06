package ru.appricot.startuphub.auth.domain

import jakarta.inject.Inject
import jakarta.inject.Singleton
import ru.appricot.startuphub.auth.repository.AuthRepository

@Singleton
class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, firstName: String, lastName: String): Result<Boolean> = try {
        val result = repository.signUp(email, firstName, lastName)
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
