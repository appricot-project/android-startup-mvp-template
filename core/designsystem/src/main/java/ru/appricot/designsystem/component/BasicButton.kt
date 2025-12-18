package ru.appricot.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.appricot.designsystem.theme.HubAppTheme
import ru.appricot.designsystem.theme.StartupHubTheme

@Composable
fun BasicButton(onClick: () -> Unit, modifier: Modifier = Modifier, text: String = "", enabled: Boolean = true) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = HubAppTheme.colors.primaryColor,
            contentColor = HubAppTheme.colors.primaryBackground,
            disabledContainerColor = HubAppTheme.colors.inactiveColorBackground,
            disabledContentColor = HubAppTheme.colors.inactiveColorText,
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        enabled = enabled,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = if (enabled) HubAppTheme.colors.primaryBackground else HubAppTheme.colors.secondaryColorText,
            modifier = Modifier.padding(vertical = 6.dp),
        )
    }
}

@Composable
fun WhiteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, HubAppTheme.colors.primaryColorText),
        colors = ButtonDefaults.buttonColors(
            containerColor = HubAppTheme.colors.primaryBackground,
            contentColor = HubAppTheme.colors.primaryColorText,
        ),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        Text(
            text = text,
            style = textStyle,
            color = HubAppTheme.colors.primaryColorText,
        )
    }
}

@Preview(name = "BasicButton - Disabled", group = "BasicButton", showBackground = true)
@Preview(
    name = "BasicButton - Disabled (Dark)",
    group = "BasicButton",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun BasicButtonDisabledPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                BasicButton(
                    text = "Submit",
                    onClick = {},
                    enabled = false,
                )

                Spacer(modifier = Modifier.height(16.dp))

                BasicButton(
                    text = "Save Changes",
                    onClick = {},
                    enabled = false,
                )
            }
        }
    }
}

@Preview(name = "BasicButton - Empty Text", group = "BasicButton", showBackground = true)
@Composable
private fun BasicButtonEmptyTextPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                BasicButton(
                    text = "",
                    onClick = {},
                    enabled = true,
                )
            }
        }
    }
}

@Preview(name = "BasicButton - Long Text", group = "BasicButton", showBackground = true)
@Composable
private fun BasicButtonLongTextPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                BasicButton(
                    text = "This is a very long button text that should wrap or truncate",
                    onClick = {},
                    enabled = true,
                )
            }
        }
    }
}

@Preview(name = "BasicButton - Fixed Width", group = "BasicButton", showBackground = true)
@Composable
private fun BasicButtonFixedWidthPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BasicButton(
                    text = "Center Button",
                    onClick = {},
                    modifier = Modifier.width(200.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                BasicButton(
                    text = "Small",
                    onClick = {},
                    modifier = Modifier.width(100.dp),
                )
            }
        }
    }
}

// ---------- PREVIEWS для WhiteButton ----------

@Preview(name = "WhiteButton - Default", group = "WhiteButton", showBackground = true)
@Preview(
    name = "WhiteButton - Default (Dark)",
    group = "WhiteButton",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun WhiteButtonDefaultPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                WhiteButton(
                    text = "Cancel",
                    onClick = {},
                )

                Spacer(modifier = Modifier.height(16.dp))

                WhiteButton(
                    text = "Edit Profile",
                    onClick = {},
                )
            }
        }
    }
}

@Preview(name = "WhiteButton - Custom Text Style", group = "WhiteButton", showBackground = true)
@Composable
private fun WhiteButtonCustomStylePreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                WhiteButton(
                    text = "Bold Button",
                    textStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    onClick = {},
                )

                Spacer(modifier = Modifier.height(16.dp))

                WhiteButton(
                    text = "Small Button",
                    textStyle = MaterialTheme.typography.bodySmall,
                    onClick = {},
                )
            }
        }
    }
}

@Preview(name = "WhiteButton - Fixed Width", group = "WhiteButton", showBackground = true)
@Composable
private fun WhiteButtonFixedWidthPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WhiteButton(
                    text = "Medium",
                    onClick = {},
                    modifier = Modifier.width(200.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                WhiteButton(
                    text = "X-Small",
                    onClick = {},
                    modifier = Modifier.width(80.dp),
                )
            }
        }
    }
}

@Preview(name = "WhiteButton - Long Text", group = "WhiteButton", showBackground = true)
@Composable
private fun WhiteButtonLongTextPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                WhiteButton(
                    text = "Button with very long text that might not fit",
                    onClick = {},
                )
            }
        }
    }
}

// ---------- COMBINED PREVIEWS ----------

@Preview(name = "Button Comparison", group = "Comparison", showBackground = true, widthDp = 360)
@Preview(
    name = "Button Comparison (Dark)",
    group = "Comparison",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun ButtonsComparisonPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = "Basic Buttons",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                BasicButton(
                    text = "Enabled Basic Button",
                    onClick = {},
                    enabled = true,
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicButton(
                    text = "Disabled Basic Button",
                    onClick = {},
                    enabled = false,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "White Buttons",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                WhiteButton(
                    text = "White Button",
                    onClick = {},
                )

                Spacer(modifier = Modifier.height(8.dp))

                WhiteButton(
                    text = "White Button with Custom Style",
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    onClick = {},
                )
            }
        }
    }
}

@Preview(name = "Buttons in Row", group = "Layout", showBackground = true)
@Composable
private fun ButtonsInRowPreview() {
    StartupHubTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    BasicButton(
                        text = "Save",
                        onClick = {},
                        modifier = Modifier.weight(1f),
                    )

                    WhiteButton(
                        text = "Cancel",
                        onClick = {},
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    WhiteButton(
                        text = "Back",
                        onClick = {},
                        modifier = Modifier.weight(1f),
                    )

                    BasicButton(
                        text = "Next",
                        onClick = {},
                        modifier = Modifier.weight(2f),
                    )
                }
            }
        }
    }
}
