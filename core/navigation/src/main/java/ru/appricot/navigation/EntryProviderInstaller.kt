package ru.appricot.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

typealias EntryProviderInstaller = EntryProviderScope<NavKey>.(Navigator) -> Unit
