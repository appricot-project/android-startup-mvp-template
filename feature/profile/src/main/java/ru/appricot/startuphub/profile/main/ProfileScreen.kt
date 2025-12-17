package ru.appricot.startuphub.profile.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.appricot.designsystem.component.BasicButton
import ru.appricot.designsystem.component.BasicLoader
import ru.appricot.designsystem.component.TopAppBar

@Composable
fun ProfileScreen(onHomeClick: (String) -> Unit, modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel()) {
    val startupsState by viewModel.state.collectAsStateWithLifecycle()
    Content(
        startupsState = startupsState,
        onHomeClick = onHomeClick,
        modifier = modifier
    )
}

@Composable
fun Content(startupsState: StartupsUiState, onHomeClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = "Profile") }
    ) { paddingValues ->
        when (startupsState) {
            is StartupsUiState.Loading -> BasicLoader()

            is StartupsUiState.Data ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    content = {
                        Text(
                            text = "Profile Screen",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        BasicButton(
                            modifier = Modifier.padding(16.dp),
                            text = "To Home",
                            onClick = {
                                onHomeClick("12345")
                            }
                        )
                    }
                )
        }
    }
}
