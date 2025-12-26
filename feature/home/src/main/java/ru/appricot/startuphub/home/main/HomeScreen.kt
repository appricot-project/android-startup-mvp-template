package ru.appricot.startuphub.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.appricot.designsystem.component.BasicLoader
import ru.appricot.designsystem.component.TopAppBar
import ru.appricot.startuphub.startups.model.StartupModel
import ru.appricot.startuphub.ui.ErrorAlert

@Composable
fun HomeScreen(onDetailsClick: (Int) -> Unit, modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    val startupsState by viewModel.state.collectAsStateWithLifecycle()
    ErrorAlert(viewModel.errors)
    Content(
        startupsState = startupsState,
        onDetailsClick = { onDetailsClick(it.id) },
        modifier = modifier,
    )
}

@Composable
fun Content(startupsState: StartupsUiState, onDetailsClick: (StartupModel) -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = "Home") },
    ) { paddingValues ->
        when (startupsState) {
            is StartupsUiState.Loading -> BasicLoader()

            is StartupsUiState.Data ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(paddingValues = paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    content = {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(
                                items = startupsState.list,
                                key = { it.id },
                            ) { startup ->
                                StartupCard(
                                    startup = startup,
                                    onItemClick = onDetailsClick,
                                    modifier = Modifier.animateItem(),
                                )
                            }
                        }
                    },
                )

            else -> Unit
        }
    }
}
