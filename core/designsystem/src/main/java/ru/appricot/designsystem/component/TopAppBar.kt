package ru.appricot.designsystem.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import ru.appricot.designsystem.theme.HubAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier, title: String? = null, titleTextStyle: TextStyle = MaterialTheme.typography.bodyLarge) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            title?.let {
                Text(
                    text = it,
                    style = titleTextStyle,
                    color = HubAppTheme.colors.primaryColorText
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HubAppTheme.colors.primaryBackground,
            navigationIconContentColor = HubAppTheme.colors.primaryColorText,
            titleContentColor = HubAppTheme.colors.primaryColorText,
            actionIconContentColor = HubAppTheme.colors.primaryColorText
        )
    )
}
