package ru.appricot.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Class for representing navigation keys in the app.
 *
 * Note: We use a sealed class because KotlinX Serialization handles
 * polymorphic serialization of sealed classes automatically.
 *
 * @param requiresLogin - true if the navigation key requires that the user is logged in
 * to navigate to it
 */
@Serializable
interface ConditionalNavKey : NavKey {
    fun requiresLogin(): Boolean = false
}
