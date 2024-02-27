package uz.john.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineManiaTopBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    leadingContent: @Composable () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        navigationIcon = leadingContent,
        title = {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        },
        scrollBehavior = scrollBehavior
    )
}