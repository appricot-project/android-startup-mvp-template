package ru.appricot.navigation

import androidx.navigation3.runtime.NavKey
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class Navigator(
    val state: NavigationState,
    private val onNavigateToRestrictedKey: (targetKey: ConditionalNavKey?) -> ConditionalNavKey,
    private val isLoggedIn: () -> Boolean,
) {
    fun navigate(route: NavKey) {
        if (route is ConditionalNavKey && route.requiresLogin() && !isLoggedIn()) {
            val loginKey = onNavigateToRestrictedKey(route)
            if (route in state.backStacks.keys) {
                // This is a top level route, just switch to it?
                state.topLevelRoute = route
                state.backStacks[route]?.add(loginKey)
            } else {
                state.backStacks[state.topLevelRoute]?.add(route)
            }
        } else {
            if (route in state.backStacks.keys) {
                // This is a top level route, just switch to it?
                state.topLevelRoute = route
            } else {
                state.backStacks[state.topLevelRoute]?.add(route)
            }
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute] ?: error("Stack for ${state.topLevelRoute} not found")
        val currentRoute = currentStack.last()

        // If we're at the base of the current route, go back to the start route stack.
        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}
