package uz.john.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.john.ui.R
import uz.john.ui.theme.CineManiaTheme

private val BUTTON_SIZE = 32.dp
private val BUTTON_SHAPE_ROUND = 12.dp

@Composable
fun CineManiaBackButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(BUTTON_SIZE)
            .clip(RoundedCornerShape(BUTTON_SHAPE_ROUND))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
    }
}

@Preview
@Composable
private fun BackButtonPreview() {
    CineManiaTheme {
        CineManiaBackButton(onClick = {})
    }
}