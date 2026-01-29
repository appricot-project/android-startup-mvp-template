package ru.appricot.startuphub.authapi

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.appricot.navigation.ConditionalNavKey

@Serializable
data class Auth(val redirectToKey: ConditionalNavKey? = null) : ConditionalNavKey

@Serializable
object SignIn : NavKey

@Serializable
data class SignUp(val email: String? = null) : NavKey
