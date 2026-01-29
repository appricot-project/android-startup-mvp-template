package ru.appricot.profileapi

import kotlinx.serialization.Serializable
import ru.appricot.navigation.ConditionalNavKey

@Serializable
object Profile : ConditionalNavKey {
    override fun requiresLogin(): Boolean = true
}
