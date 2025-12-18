package ru.appricot.startuphub.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.appricot.designsystem.component.WhiteButton

@Composable
fun StartupDetailsScreen(viewModel: StartupDetailsViewModel, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    val id = viewModel.id
    Content(
        id = id,
        onButtonClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
fun Content(id: Long?, onButtonClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Text(
                text = "Details Screen: $id",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            WhiteButton(
                text = "Back",
                modifier = Modifier.padding(16.dp),
                onClick = { onButtonClick() },
            )
        },
    )
}
