package uz.john.details.movie_details_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.john.ui.components.shimmerEffect
import kotlin.random.Random

private val MOVIE_IMAGE_HEIGHT = 300.dp
private val MOVIE_IMAGE_WIDTH = 200.dp

@Composable
fun MovieDetailsShimmerEffect(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(.7f)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(MOVIE_IMAGE_HEIGHT)
                .width(MOVIE_IMAGE_WIDTH)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(16.dp)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(.5f)
                .height(16.dp)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(.5f)
                .height(16.dp)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column {
            repeat(10) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(generateFloatRange(.5f))
                        .height(16.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

private fun generateFloatRange(seed: Float): Float {
    return Random.nextFloat() * seed + seed
}