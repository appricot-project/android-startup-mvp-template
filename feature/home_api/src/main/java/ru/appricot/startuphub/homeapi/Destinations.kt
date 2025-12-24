package ru.appricot.startuphub.homeapi

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object Home : NavKey

@Serializable
data class StartupDetails(val id: Int) : NavKey
