package ru.appricot.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPInputField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxOTPCount: Int = 6,
    isError: Boolean = false,
    onOTPFill: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = { textField ->
                if (textField.text.length <= maxOTPCount) {
                    val otpFilled: Boolean = textField.text.length == maxOTPCount
                    onOtpTextChange.invoke(textField.text)
                    if (otpFilled) {
                        onOTPFill.invoke(textField.text)
                    }
                }
            },
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    repeat(maxOTPCount) { index ->
                        val char = when {
                            index >= otpText.length -> ""
                            else -> otpText[index].toString()
                        }

                        val isCurrentChar: Boolean = char.isEmpty() && index == otpText.length
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(48.dp)
                                .height(48.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(12.dp),
                                ),
                        ) {
                            val borderColor = when {
                                isError -> MaterialTheme.colorScheme.error
                                isCurrentChar -> MaterialTheme.colorScheme.onSurfaceVariant
                                else -> Color.Unspecified
                            }
                            Text(
                                modifier = Modifier
                                    .size(48.dp)
                                    .then(
                                        Modifier.border(
                                            2.dp,
                                            borderColor,
                                            RoundedCornerShape(12.dp),
                                        ),
                                    )
                                    .padding(top = 14.dp),
                                text = char,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    textAlign = TextAlign.Center,
                                    fontSize = 24.sp,
                                    lineHeight = 20.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
            ),
            cursorBrush = Brush.verticalGradient(
                0.0f to Color.Transparent,
                0.1f to Color.Transparent,
                0.1f to MaterialTheme.colorScheme.onPrimary,
                0.75f to MaterialTheme.colorScheme.onPrimary,
                0.75f to Color.Transparent,
                1.0f to Color.Transparent,
            ),
            singleLine = true,
        )
    }
}
