package ru.appricot.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.appricot.designsystem.theme.HubAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBackClick: (() -> Unit)? = null,
    titleTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            title?.let {
                Text(
                    text = it,
                    style = titleTextStyle,
                    color = HubAppTheme.colors.primaryColorText,
                )
            }
        },
        navigationIcon = {
            if (onBackClick != null) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .clickable(onClick = onBackClick)
                        .padding(16.dp),
                )
            }
        },
        /*colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HubAppTheme.colors.secondaryBackground,
            navigationIconContentColor = HubAppTheme.colors.primaryColorText,
            titleContentColor = HubAppTheme.colors.primaryColorText,
            actionIconContentColor = HubAppTheme.colors.primaryColorText,
        ),*/
    )
}
