package uz.john.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun VerticalGradient(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background.copy(.6f),
        Color.Transparent
    ),
    topToBottom: Boolean = false
) {
    Canvas(
        modifier = modifier
    ) {
        val gradientBrush = Brush.verticalGradient(
            colors = colors,
            startY = if (topToBottom) 0f else size.height,
            endY = if (topToBottom) size.height else 0f
        )
        drawRect(
            brush = gradientBrush,
            topLeft = Offset.Zero,
            size = size
        )
    }
}