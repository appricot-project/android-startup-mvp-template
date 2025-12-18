package ru.appricot.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.appricot.designsystem.theme.StartupHubTheme

@Composable
fun NavigationBar(
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit,
    destinations: Map<NavKey, NavBarItem>,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = modifier,
    ) {
        destinations.forEach { (topLevelDestination, data) ->
            NavigationBarItem(
                selected = topLevelDestination == selectedKey,
                onClick = {
                    onSelectKey(topLevelDestination)
                },
                icon = {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = data.title,
                    )
                },
                label = {
                    Text(data.title)
                },
            )
        }
    }
}

// ---------- Вспомогательные классы для Preview ----------
// Определяем несколько тестовых NavKey для Preview
@Serializable
private object Home : NavKey

@Serializable
private object Profile : NavKey

@Serializable
private object Search : NavKey

@Serializable
private object Notifications : NavKey

@Serializable
private object Settings : NavKey

// ---------- PREVIEWS ----------

@Preview(name = "NavigationBar - 3 Items", group = "Standard", showBackground = true)
@Preview(
    name = "NavigationBar - 3 Items (Dark)",
    group = "Standard",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun NavigationBar3ItemsPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Home,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Selected: Home", group = "Selection States")
@Composable
private fun NavigationBarSelectedHomePreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Home,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Selected: Search", group = "Selection States")
@Composable
private fun NavigationBarSelectedSearchPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Search,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Selected: Profile", group = "Selection States")
@Composable
private fun NavigationBarSelectedProfilePreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Profile,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "NavigationBar - 4 Items", group = "Item Count")
@Preview(
    name = "NavigationBar - 4 Items (Dark)",
    group = "Item Count",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun NavigationBar4ItemsPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf<NavKey, NavBarItem>(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Notifications to NavBarItem(Icons.Default.Notifications, "Notifications", "Notifications"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Search,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "NavigationBar - 5 Items", group = "Item Count")
@Preview(
    name = "NavigationBar - 5 Items (Dark)",
    group = "Item Count",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun NavigationBar5ItemsPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf<NavKey, NavBarItem>(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Notifications to NavBarItem(Icons.Default.Notifications, "Notifications", "Notifications"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
                Settings to NavBarItem(Icons.Default.Settings, "Settings", "Settings"),
            )

            NavigationBar(
                selectedKey = Notifications,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "NavigationBar - 2 Items", group = "Item Count")
@Composable
private fun NavigationBar2ItemsPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Profile,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Custom Icons", group = "Customization")
@Composable
private fun NavigationBarCustomIconsPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf<NavKey, NavBarItem>(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Notifications to NavBarItem(Icons.Default.Notifications, "Notifications", "Notifications"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Search,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Long Text Labels", group = "Edge Cases")
@Composable
private fun NavigationBarLongLabelsPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Home,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Very Long Text", group = "Edge Cases")
@Composable
private fun NavigationBarVeryLongLabelsPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home Dashboard", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search Dashboard", "Search"),
            )

            NavigationBar(
                selectedKey = Search,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Different Widths", group = "Layout")
@Composable
private fun NavigationBarDifferentWidthsPreview() {
    StartupHubTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Width: 320dp", style = MaterialTheme.typography.labelSmall)
            Surface(
                modifier = Modifier.width(320.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                val destinations = mapOf(
                    Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                    Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                    Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
                )

                NavigationBar(
                    selectedKey = Home,
                    onSelectKey = {},
                    destinations = destinations,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Width: 240dp", style = MaterialTheme.typography.labelSmall)
            Surface(
                modifier = Modifier.width(240.dp),
                shape = RoundedCornerBottomShape(16.dp),
            ) {
                val destinations = mapOf(
                    Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                    Profile to NavBarItem(Icons.Default.Person, "Me", "Profile"),
                )

                NavigationBar(
                    selectedKey = Profile,
                    onSelectKey = {},
                    destinations = destinations,
                )
            }
        }
    }
}

// Специальная форма для Preview
@Composable
private fun RoundedCornerBottomShape(cornerSize: Dp) = RoundedCornerShape(
    topStart = cornerSize,
    topEnd = cornerSize,
    bottomStart = 0.dp,
    bottomEnd = 0.dp,
)

@Preview(name = "In Context Screen", group = "Integration", showBackground = true, heightDp = 640)
@Composable
private fun NavigationBarInContextPreview() {
    StartupHubTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Имитация контента экрана
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp), // Место для BottomBar
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Main Content",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Navigation bar is at the bottom",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // NavigationBar внизу
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            ) {
                val destinations = mapOf<NavKey, NavBarItem>(
                    Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                    Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                    Notifications to NavBarItem(Icons.Default.Notifications, "Notifications", "Notifications"),
                    Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
                )

                NavigationBar(
                    selectedKey = Home,
                    onSelectKey = {},
                    destinations = destinations,
                )
            }
        }
    }
}

@Preview(name = "Interactive Preview", group = "Interactive")
@Composable
private fun NavigationBarInteractivePreview() {
    StartupHubTheme {
        var selectedKey by remember { mutableStateOf<NavKey>(Home) }

        val destinations = mapOf<NavKey, NavBarItem>(
            Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
            Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
            Notifications to NavBarItem(Icons.Default.Notifications, "Notifications", "Notifications"),
            Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Отображение текущего выбора
            Text(
                text = "Selected: ${selectedKey::class.simpleName}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
            )

            // Инструкция
            Text(
                text = "Click on navigation items below to test selection",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(32.dp))

            // NavigationBar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
            ) {
                NavigationBar(
                    selectedKey = selectedKey,
                    onSelectKey = { newKey -> selectedKey = newKey },
                    destinations = destinations,
                )
            }
        }
    }
}

// Быстрые Preview для разработки
@Preview(name = "Quick Preview", group = "Dev", showBackground = true)
@Composable
private fun NavigationBarQuickPreview() {
    StartupHubTheme {
        Surface {
            val destinations = mapOf(
                Home to NavBarItem(Icons.Default.Home, "Home", "Home"),
                Search to NavBarItem(Icons.Default.Search, "Search", "Search"),
                Profile to NavBarItem(Icons.Default.Person, "Profile", "Profile"),
            )

            NavigationBar(
                selectedKey = Home,
                onSelectKey = {},
                destinations = destinations,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
