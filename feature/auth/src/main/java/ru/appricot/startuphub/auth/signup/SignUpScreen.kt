package ru.appricot.startuphub.auth.signup

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
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
fun SignUpScreen(
    onSuccessRegistration: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel<SignUpViewModel>(),
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val firstName by viewModel.firstName.collectAsStateWithLifecycle()
    val lastName by viewModel.lastName.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val emailValidationError by viewModel.emailValidationError.collectAsStateWithLifecycle()
    val firstNameValidationError by viewModel.firstNameValidationError.collectAsStateWithLifecycle()
    val lastNameValidationError by viewModel.lastNameValidationError.collectAsStateWithLifecycle()

    val doOnSuccess by rememberUpdatedState(onSuccessRegistration)

    ErrorAlert(viewModel.errors)
    LaunchedEffect(state) {
        if (state is SignUpUiState.Registered) doOnSuccess()
    }
    Box(
        modifier = modifier
            .padding(top = 48.dp, bottom = 16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state is SignUpUiState.Loading) {
            Spacer(modifier = Modifier.height(16.dp))
            BasicLoader(
                modifier = Modifier.align(Alignment.Center),
                backgroundColor = Color.Transparent,
            )
        }

        SignUpContent(
            email = email,
            firstName = firstName,
            lastName = lastName,
            state = state,
            emailValidationError = emailValidationError,
            firstNameValidationError = firstNameValidationError,
            lastNameValidationError = lastNameValidationError,
            updateEmail = viewModel::updateEmail,
            updateFirstName = viewModel::updateFirstName,
            updateLastName = viewModel::updateLastName,
            onSignUpClick = { viewModel.signUp() },
        )
    }
}

@Composable
fun SignUpContent(
    state: SignUpUiState,
    email: String,
    firstName: String,
    lastName: String,
    emailValidationError: Int?,
    firstNameValidationError: Int?,
    lastNameValidationError: Int?,
    updateEmail: (String) -> Unit,
    updateFirstName: (String) -> Unit,
    updateLastName: (String) -> Unit,
    onSignUpClick: () -> Unit,
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
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.enter_your_details_to_create_account),
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
                imeAction = ImeAction.Next,
            ),
            enabled = state !is SignUpUiState.Loading,
            errorMessage = if (emailValidationError != null) stringResource(emailValidationError) else null,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicAppTextField(
            value = firstName,
            onValueChange = updateFirstName,
            placeholder = stringResource(R.string.first_name),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words,
            ),
            enabled = state !is SignUpUiState.Loading,
            errorMessage = if (firstNameValidationError != null) stringResource(firstNameValidationError) else null,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicAppTextField(
            value = lastName,
            onValueChange = updateLastName,
            placeholder = stringResource(R.string.last_name),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
            ),
            enabled = state !is SignUpUiState.Loading,
            errorMessage = if (lastNameValidationError != null) stringResource(lastNameValidationError) else null,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            onClick = onSignUpClick,
            text = stringResource(R.string.next),
            enabled = state !is SignUpUiState.Loading &&
                email.isNotBlank() &&
                firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                emailValidationError == null &&
                firstNameValidationError == null &&
                lastNameValidationError == null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
