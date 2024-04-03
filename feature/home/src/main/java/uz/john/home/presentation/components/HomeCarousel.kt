package uz.john.home.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.tv_show.TvShow
import uz.john.ui.components.CoilImage
import uz.john.ui.components.DotsIndicator
import uz.john.ui.components.VerticalGradient
import kotlin.math.absoluteValue

private const val AUTO_SCROLL_DELAY = 4000L
private val PAGER_HEIGHT = 200.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesCarouselItem(
    pagerState: PagerState,
    moviesList: List<Movie>,
    title: String,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(pagerState.currentPage) {
        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
        delay(AUTO_SCROLL_DELAY)
        pagerState.scrollToPage(nextPage)
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            state = pagerState,
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)

            Box(
                modifier = Modifier
                    .height(PAGER_HEIGHT)
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        onItemClick(moviesList[page].id)
                    }
            ) {
                CoilImage(
                    modifier = Modifier,
                    imagePath = moviesList[page].backdropPath,
                    imageSize = NetworkImageSizes.LARGE
                )

                VerticalGradient(
                    modifier = Modifier.fillMaxSize(),
                    colors = listOf(
                        Color.Black,
                        Color.Black.copy(alpha = .4f),
                        Color.Transparent
                    ),
                )

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = moviesList[page].title,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = moviesList[page].releaseDate,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DotsIndicator(pagerState = pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvShowsCarouselItem(
    pagerState: PagerState,
    tvShowList: List<TvShow>,
    title: String,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(pagerState.currentPage) {
        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
        delay(AUTO_SCROLL_DELAY)
        pagerState.scrollToPage(nextPage)
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            state = pagerState,
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)

            Box(
                modifier = Modifier
                    .height(PAGER_HEIGHT)
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        onItemClick(tvShowList[page].id)
                    }
            ) {
                CoilImage(
                    modifier = Modifier,
                    imagePath = tvShowList[page].backdropPath,
                    imageSize = NetworkImageSizes.LARGE
                )

                VerticalGradient(
                    modifier = Modifier.fillMaxSize(),
                    colors = listOf(
                        Color.Black,
                        Color.Black.copy(alpha = .4f),
                        Color.Transparent
                    ),
                )

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = tvShowList[page].name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = tvShowList[page].firstAirDate,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DotsIndicator(pagerState = pagerState)
    }
}