package uz.john.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.john.ui.theme.CineManiaIcons

private val LOGO_SIZE = 150.dp

@Composable
fun CineManiaGradientIcon(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        Color.Black.copy(alpha = .8f)
    )
) {
    val brush = Brush.verticalGradient(colors = colors)

    Icon(
        modifier = modifier
            .size(LOGO_SIZE)
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = brush,
                        blendMode = BlendMode.SrcAtop
                    )
                }
            },
        painter = painterResource(CineManiaIcons.CineManiaLogo),
        contentDescription = null
    )
}