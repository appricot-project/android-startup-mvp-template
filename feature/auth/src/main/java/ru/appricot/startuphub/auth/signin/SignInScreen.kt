package ru.appricot.startuphub.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.appricot.designsystem.component.BasicAppTextField
import ru.appricot.designsystem.component.BasicButton
import ru.appricot.designsystem.component.BasicLoader
import ru.appricot.startuphub.ui.ErrorAlert
import ru.apprictor.startuphub.auth.R

@Composable
fun SignInScreen(
    onNavigateToCode: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel<SignInViewModel>(),
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val emailValidationError by viewModel.emailValidationError.collectAsStateWithLifecycle()

    ErrorAlert(viewModel.errors)
    Box(
        modifier = modifier
            .padding(top = 48.dp, bottom = 16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state is SignInUiState.Loading) {
            Spacer(modifier = Modifier.height(16.dp))
            BasicLoader(
                modifier = Modifier.align(Alignment.Center),
                backgroundColor = Color.Transparent,
            )
        }
        SignInContent(
            email = email,
            state = state,
            emailValidationError = emailValidationError,
            updateEmail = viewModel::updateEmail,
            onNextClick = viewModel::signIn,
        )
    }
}

@Composable
fun SignInContent(
    state: SignInUiState,
    email: String,
    emailValidationError: String?,
    updateEmail: (String) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.enter_your_email_to_continue),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        Spacer(modifier = Modifier.height(48.dp))

        BasicAppTextField(
            value = email,
            onValueChange = updateEmail,
            placeholder = stringResource(R.string.email_address),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            enabled = state !is SignInUiState.Loading,
            errorMessage = emailValidationError,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            onClick = onNextClick,
            text = stringResource(R.string.next),
            enabled = state !is SignInUiState.Loading && email.isNotBlank() && emailValidationError == null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
