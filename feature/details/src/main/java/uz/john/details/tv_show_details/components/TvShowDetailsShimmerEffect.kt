package uz.john.details.tv_show_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.john.ui.components.shimmerEffect
import kotlin.random.Random

private val TV_SHOW_IMAGE_HEIGHT = 200.dp
private val TV_SHOW_IMAGE_WIDTH = 300.dp

@Composable
fun TvShowDetailsShimmerEffect(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(16.dp)
                    .fillMaxWidth(.5f)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .height(TV_SHOW_IMAGE_HEIGHT)
                    .width(TV_SHOW_IMAGE_WIDTH)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(.3f)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(.2f)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(.2f)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                for (i in 1..10) {
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(generateFloatRange(.5f))
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

private fun generateFloatRange(seed: Float): Float {
    return Random.nextFloat() * seed + seed
}