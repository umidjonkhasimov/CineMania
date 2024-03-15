package uz.john.details.person_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.john.ui.components.shimmerEffect
import kotlin.random.Random

private val SCREEN_PADDING = 16.dp
private val PERSON_IMAGE_HEIGHT = 230.dp
private val PERSON_IMAGE_WIDTH = 150.dp

@Composable
fun PersonDetailsShimmerEffect() {
    LazyColumn {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.6f)
                        .padding(SCREEN_PADDING)
                        .height(16.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SCREEN_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(PERSON_IMAGE_HEIGHT)
                            .width(PERSON_IMAGE_WIDTH)
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
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

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(16.dp)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(16.dp)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = SCREEN_PADDING)
                        .fillMaxWidth()
                ) {
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
    }
}

private fun generateFloatRange(seed: Float): Float {
    return Random.nextFloat() * seed + seed
}