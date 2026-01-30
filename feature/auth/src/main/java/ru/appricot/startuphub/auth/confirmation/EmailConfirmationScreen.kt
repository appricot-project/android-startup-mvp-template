package ru.appricot.startuphub.auth.confirmation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import ru.appricot.designsystem.component.BasicButton
import ru.appricot.designsystem.component.BasicLoader
import ru.appricot.designsystem.component.OTPInputField
import ru.appricot.startuphub.ui.ErrorAlert
import ru.apprictor.startuphub.auth.R

@Composable
fun EmailConfirmationScreen(
    email: String,
    onBackClick: () -> Unit,
    onConfirmationSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EmailConfirmationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val code by viewModel.code.collectAsStateWithLifecycle()
    val rememberOnConfirmationSuccess by rememberUpdatedState(onConfirmationSuccess)

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.confirmationEvent.collect {
                rememberOnConfirmationSuccess()
            }
        }
    }

    ErrorAlert(viewModel.errors)
    Box(
        modifier = modifier
            .padding(top = 48.dp, bottom = 16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state is ConfirmationUiState.Loading) {
            Spacer(modifier = Modifier.height(16.dp))
            BasicLoader(
                modifier = Modifier.align(Alignment.Center),
                backgroundColor = Color.Transparent,
            )
        }
        EmailConfirmationContent(
            email = email,
            code = code,
            errorMessage = null,
            onCodeChange = viewModel::onCodeChanged,
            onBackClick = onBackClick,
            onConfirmClick = { viewModel.onConfirmClick() },
        )
    }
}

@Composable
private fun EmailConfirmationContent(
    email: String,
    code: String,
    errorMessage: String?,
    onCodeChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.confirmation_title),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.confirmation_description, email),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        Spacer(modifier = Modifier.height(32.dp))

        OTPInputField(
            otpText = code,
            isError = errorMessage != null,
            onOtpTextChange = onCodeChange,
            modifier = Modifier.fillMaxWidth(),
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            onClick = onConfirmClick,
            text = stringResource(R.string.confirmation_action),
            enabled = code.length == 6 && errorMessage == null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
