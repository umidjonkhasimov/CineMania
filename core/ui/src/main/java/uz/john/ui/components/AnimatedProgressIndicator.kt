package uz.john.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import uz.john.ui.R

private const val ANIMATION_DURATION = 100
private const val HALF_ALPHA = .5f
private val PROGRESS_SIZE = 150.dp

@Composable
fun AnimatedProgressIndicator(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading_animation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    AnimatedVisibility(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = HALF_ALPHA)),
        visible = visible,
        enter = fadeIn(tween(ANIMATION_DURATION)),
        exit = fadeOut(tween(ANIMATION_DURATION))
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = preloaderLottieComposition,
                progress = preloaderProgress,
                modifier = Modifier
                    .size(PROGRESS_SIZE)
            )
        }
    }
}

@Preview
@Composable
private fun ProgressIndicatorPreview() {
    AnimatedProgressIndicator(true)
}