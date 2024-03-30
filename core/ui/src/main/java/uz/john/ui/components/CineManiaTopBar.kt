package uz.john.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

private val TOP_BAR_HEIGHT = 64.dp
private val SCREEN_PADDING = 16.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineManiaTopBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
    ) {
        VerticalGradient(
            modifier = Modifier
                .fillMaxSize(),
            topToBottom = true
        )
        CenterAlignedTopAppBar(
            modifier = Modifier.padding(horizontal = SCREEN_PADDING),
            navigationIcon = leadingContent ?: {},
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
            scrollBehavior = scrollBehavior
        )

        if (trailingContent != null)
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd)
            ) {
                trailingContent()
            }
    }
}