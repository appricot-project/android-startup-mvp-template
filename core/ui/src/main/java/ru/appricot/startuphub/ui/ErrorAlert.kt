package ru.appricot.startuphub.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun ErrorAlert(labels: Flow<Any>) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            labels.collect { label ->
                if (label is ErrorLabel) errorMessage.value = label.message.orEmpty()
            }
        }
    }
    if (errorMessage.value != null) {
        AlertDialog(
            onDismissRequest = {
                // Called when the user clicks outside the dialog or presses the back button
                errorMessage.value = null
            },
            title = {
                Text(text = stringResource(R.string.error_title))
            },
            text = {
                Text(text = errorMessage.value.orEmpty())
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        errorMessage.value = null
                    },
                ) {
                    Text(stringResource(R.string.error_confirm))
                }
            },
        )
    }
}
