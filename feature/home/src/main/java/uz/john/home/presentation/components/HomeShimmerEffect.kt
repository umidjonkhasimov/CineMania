package uz.john.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.john.ui.components.shimmerEffect

private val PAGER_HEIGHT = 200.dp
private val DOTS_INDICATOR_HEIGHT = 16.dp
private val MOVIES_HOLDER_HEIGHT = 250.dp
private val MOVIE_ITEM_HEIGHT = 230.dp
private val MOVIE_ITEM_WIDTH = 150.dp

@Composable
fun HomeShimmerEffect(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .height(PAGER_HEIGHT)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(DOTS_INDICATOR_HEIGHT)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        items(5) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MOVIES_HOLDER_HEIGHT)
            ) {
                Row(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .fillMaxHeight()
                            .clip(MaterialTheme.shapes.extraSmall)
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .fillMaxHeight()
                            .clip(MaterialTheme.shapes.extraSmall)
                            .shimmerEffect()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(10) {
                        Box(
                            modifier = Modifier
                                .height(MOVIE_ITEM_HEIGHT)
                                .width(MOVIE_ITEM_WIDTH)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}