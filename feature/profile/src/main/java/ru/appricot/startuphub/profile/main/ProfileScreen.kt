package ru.appricot.startuphub.profile.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.appricot.designsystem.component.BasicAppTextField
import ru.appricot.designsystem.component.BasicButton
import ru.appricot.designsystem.component.BasicLoader
import ru.appricot.designsystem.component.TopAppBar
import ru.appricot.startuphub.auth.model.UserProfile
import ru.appricot.startuphub.ui.ErrorAlert
import ru.apprictor.startuphub.profile.R

@Composable
fun ProfileScreen(onLogoutClick: () -> Unit, modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel()) {
    val profileState by viewModel.uiState.collectAsStateWithLifecycle()
    ErrorAlert(viewModel.errors)
    Content(
        profileState = profileState,
        onLogoutClick = onLogoutClick,
        modifier = modifier,
    )
}

@Composable
fun Content(profileState: ProfileUiState, onLogoutClick: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = "Profile") },
    ) { paddingValues ->
        when (profileState) {
            is ProfileUiState.Loading -> BasicLoader()

            is ProfileUiState.Data -> {
                if (profileState.profile != null) {
                    ProfileContent(
                        profile = profileState.profile,
                        onLogoutClick = onLogoutClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                    )
                }
            }

            is ProfileUiState.Error -> Unit
        }
    }
}

@Composable
fun ProfileContent(profile: UserProfile, onLogoutClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.profile_information),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                BasicAppTextField(
                    value = profile.firstName,
                    onValueChange = { },
                    label = stringResource(R.string.profile_label_first_name),
                    enabled = false,
                )

                BasicAppTextField(
                    value = profile.lastName,
                    onValueChange = { },
                    label = stringResource(R.string.profile_label_last_name),
                    enabled = false,
                )

                BasicAppTextField(
                    value = profile.email,
                    onValueChange = { },
                    label = stringResource(R.string.profile_label_email),
                    enabled = false,
                )

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                )

                BasicButton(
                    text = "Logout",
                    onClick = onLogoutClick,
                )
            }
        }
    }
}
