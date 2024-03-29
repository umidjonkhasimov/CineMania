package uz.john.search.presentation.search_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.john.ui.components.shimmerEffect

private val SCREEN_PADDING = 16.dp
private val ITEM_HEIGHT = 150.dp

@Composable
fun SearchScreenShimmerEffect(
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.padding(SCREEN_PADDING)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(24.dp)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow {
                items(10) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(ITEM_HEIGHT)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(24.dp)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Box(
                modifier = Modifier
                    .width(ITEM_HEIGHT)
                    .height(24.dp)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(modifier = Modifier.height(200.dp)) {
                items(10) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(2f / 3f)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(24.dp)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Box(
                modifier = Modifier
                    .width(ITEM_HEIGHT)
                    .height(24.dp)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(modifier = Modifier.height(200.dp)) {
                items(10) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(2f / 3f)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(24.dp)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}
