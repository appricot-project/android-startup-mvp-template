package ru.appricot.startuphub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import ru.appricot.designsystem.component.NavBarItem
import ru.appricot.designsystem.component.NavigationBar
import ru.appricot.designsystem.theme.StartupHubTheme
import ru.appricot.navigation.EntryProviderInstaller
import ru.appricot.navigation.Navigator
import ru.appricot.navigation.rememberNavigationState
import ru.appricot.navigation.toEntries
import ru.appricot.profileapi.Profile
import ru.appricot.startuphub.authapi.Auth
import ru.appricot.startuphub.homeapi.Home
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var entryBuilders: Set<@JvmSuppressWildcards EntryProviderInstaller>

    private val topLevelRoots = mapOf(
        Home to NavBarItem(icon = Icons.Default.Home, title = "Home", description = "Home"),
        Profile to NavBarItem(icon = Icons.Default.AccountCircle, title = "Profile", description = "Profile"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationState = rememberNavigationState(
                startRoute = Home,
                topLevelRoutes = topLevelRoots.keys,
            )
            var isLoggedIn by rememberSaveable {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = Unit) {
                // TODO: observe authorization token value and update isLoggedIn variable
            }
            val navigator = remember {
                Navigator(
                    state = navigationState,
                    onNavigateToRestrictedKey = { redirectToKey -> Auth(redirectToKey) },
                    isLoggedIn = { isLoggedIn },
                )
            }
            val entryProvider = entryProvider {
                entryBuilders.forEach { builder ->
                    this.builder(navigator)
                }
            }
            StartupHubTheme {
                Scaffold(bottomBar = {
                    NavigationBar(
                        selectedKey = navigationState.topLevelRoute,
                        onSelectKey = {
                            navigator.navigate(it)
                        },
                        destinations = topLevelRoots,
                    )
                }) { paddingValues ->
                    NavDisplay(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        entries = navigationState.toEntries(entryProvider),
                        onBack = { navigator.goBack() },
                        transitionSpec = {
                            // Slide in from right when navigating forward
                            slideInHorizontally(initialOffsetX = { it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { -it })
                        },
                        popTransitionSpec = {
                            // Slide in from left when navigating back
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { it })
                        },
                        predictivePopTransitionSpec = {
                            // Slide in from left when navigating back
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { it })
                        },
                    )
                }
            }
        }
    }
}
