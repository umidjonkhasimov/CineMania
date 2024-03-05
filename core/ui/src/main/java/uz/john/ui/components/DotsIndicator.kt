package uz.john.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val ANIMATION_DURATION = 300
private val DOT_SIZE = 10.dp
private val EXPANDED_DOT_SIZE = 30.dp
private val SPACE_BETWEEN_DOTS = 8.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotsIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    animationDuration: Int = ANIMATION_DURATION,
    dotSize: Dp = DOT_SIZE,
    expandedDotSize: Dp = EXPANDED_DOT_SIZE,
    spaceBetweenDots: Dp = SPACE_BETWEEN_DOTS
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { selectedPage ->
            val isSelected = pagerState.currentPage == selectedPage
            val animatedWidth = animateDpAsState(
                targetValue = if (isSelected) expandedDotSize else dotSize,
                label = "animatedWidth",
                animationSpec = tween(animationDuration)
            )

            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .width(animatedWidth.value)
                    .height(dotSize)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.primary.copy(alpha = .3f)
                    )
            )
            if (selectedPage != pagerState.pageCount - 1)
                Spacer(modifier = Modifier.width(spaceBetweenDots))
        }
    }
}