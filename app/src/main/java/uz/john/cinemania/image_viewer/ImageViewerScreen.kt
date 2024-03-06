package uz.john.cinemania.image_viewer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import uz.john.domain.model.NetworkImageSizes
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.components.CoilImage

private const val ANIMATION_DURATION = 700

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ImageViewerScreen(
    imagePath: String,
    onBackClick: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        label = "animatedScale",
        animationSpec = tween(ANIMATION_DURATION)
    )
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    val animatedOffset by animateOffsetAsState(
        targetValue = offset,
        label = "animatedOffset",
        animationSpec = tween(ANIMATION_DURATION)
    )

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            CoilImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.70f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (scale >= 4f) {
                                    scale = 1f
                                    offset = Offset(0f, 0f)
                                } else {
                                    scale *= 2f
                                }
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, _, _ ->
                            offset = if (scale == 1f)
                                Offset(0f, 0f)
                            else offset + pan
                        }
                    }
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                        translationX = animatedOffset.x
                        translationY = animatedOffset.y
                    },
                imagePath = "/$imagePath",
                imageSize = NetworkImageSizes.ORIGINAL
            )
            CineManiaTopBar(
                modifier = Modifier.align(Alignment.TopCenter),
                title = "",
                leadingContent = {
                    CineManiaBackButton(
                        onClick = onBackClick
                    )
                }
            )
        }
    }
}