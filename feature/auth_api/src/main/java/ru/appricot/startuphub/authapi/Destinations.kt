package ru.appricot.startuphub.authapi

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object SignIn : NavKey

@Serializable
data class SignUp(val email: String? = null) : NavKey

@Serializable
data class EmailConfirmation(val email: String) : NavKey
