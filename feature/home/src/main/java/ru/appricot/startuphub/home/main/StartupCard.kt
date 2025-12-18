package ru.appricot.startuphub.home.main

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.appricot.designsystem.theme.StartupHubTheme
import ru.apprictor.startuphub.home.BuildConfig

@Composable
fun StartupCard(startup: StartupModel, modifier: Modifier = Modifier, onItemClick: (StartupModel) -> Unit = {}) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick(startup) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            // Название стартапа
            Text(
                text = startup.name ?: "Unknown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Категория и город в строке
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Категория с иконкой
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = getCategoryIcon(startup.category),
                        contentDescription = "Category icon",
                        modifier = Modifier.size(16.dp),
                        tint = getCategoryColor(startup.category),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = startup.category ?: "N/A",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = getCategoryColor(startup.category),
                    )
                }

                // Город с иконкой
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = startup.city ?: "Unknown",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

            if (BuildConfig.DEBUG) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "ID: ${startup.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

@Composable
private fun getCategoryIcon(category: String?): ImageVector = when (category?.uppercase()) {
    "AI" -> Icons.Default.SmartToy
    "FINTECH" -> Icons.Default.AccountBalance
    "HEALTH" -> Icons.Default.MedicalServices
    else -> Icons.Default.Business
}

@Composable
private fun getCategoryColor(category: String?): Color = when (category?.uppercase()) {
    "AI" -> Color(0xFF4285F4)
    "FINTECH" -> Color(0xFF34A853)
    "HEALTH" -> Color(0xFFEA4335)
    else -> MaterialTheme.colorScheme.onSurfaceVariant
}

@Preview(name = "AI Startup", group = "Category")
@Preview(name = "AI Startup (Dark)", group = "Category", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StartupItemAIPreview() {
    StartupHubTheme {
        Surface {
            StartupCard(
                startup = StartupModel(
                    id = 1,
                    name = "NeuroGen",
                    category = "AI",
                    city = "Moscow",
                ),
            )
        }
    }
}

@Preview(name = "Fintech Startup", group = "Category")
@Preview(name = "Fintech Startup (Dark)", group = "Category", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StartupItemFintechPreview() {
    StartupHubTheme {
        Surface {
            StartupCard(
                startup = StartupModel(
                    id = 5,
                    name = "PayWave",
                    category = "Fintech",
                    city = "Washington",
                ),
            )
        }
    }
}

@Preview(name = "Health Startup", group = "Category")
@Preview(name = "Health Startup (Dark)", group = "Category", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StartupItemHealthPreview() {
    StartupHubTheme {
        Surface {
            StartupCard(
                startup = StartupModel(
                    id = 7,
                    name = "HeartWell",
                    category = "Health",
                    city = "London",
                ),
            )
        }
    }
}

@Preview(name = "Long Name", group = "Edge Cases")
@Composable
private fun StartupItemLongNamePreview() {
    StartupHubTheme {
        Surface {
            StartupCard(
                startup = StartupModel(
                    id = 10,
                    name = "Very Long Startup Name That Should Be Truncated",
                    category = "AI",
                    city = "San Francisco",
                ),
            )
        }
    }
}

@Preview(name = "Null Values", group = "Edge Cases")
@Preview(name = "Null Values (Dark)", group = "Edge Cases", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StartupItemNullValuesPreview() {
    StartupHubTheme {
        Surface {
            StartupCard(
                startup = StartupModel(
                    id = 11,
                    name = null,
                    category = null,
                    city = null,
                ),
            )
        }
    }
}
