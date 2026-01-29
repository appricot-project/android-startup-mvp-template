package ru.appricot.startuphub.auth.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.appricot.designsystem.component.BasicButton
import ru.appricot.designsystem.component.WhiteButton
import ru.apprictor.startuphub.auth.R

@Composable
fun AuthChoiceScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(top = 48.dp, bottom = 16.dp).fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 24.dp),
        ) {
            Text(
                text = stringResource(R.string.welcome_to_startup_hub),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.choose_authentication_method),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outlineVariant,
            )

            Spacer(modifier = Modifier.height(48.dp))
            Spacer(modifier = Modifier.weight(1f))

            BasicButton(
                onClick = onLoginClick,
                text = stringResource(R.string.sign_in),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            WhiteButton(
                onClick = onRegisterClick,
                text = stringResource(R.string.create_account),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthChoiceScreenPreview() {
    AuthChoiceScreen(
        onLoginClick = {},
        onRegisterClick = {},
    )
}
