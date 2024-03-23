package uz.john.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.tv_show.TvShow
import uz.john.ui.theme.CineManiaColors

private const val ITEM_RATIO = 2f / 3f

@Composable
fun TvShowItem(
    tvShow: TvShow,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .aspectRatio(ratio = ITEM_RATIO)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.small)
                .clickable {
                    onTvShowClick(tvShow.id)
                }
        ) {
            StackedCoilImage(tvShow = tvShow)

            Rating(tvShow = tvShow)
        }

        Text(
            text = tvShow.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BoxScope.Rating(
    tvShow: TvShow
) {
    val rating = tvShow.voteAverage

    val ratingBackground =
        if (rating < 7.0) CineManiaColors.Gray
        else if (rating < 8.0) CineManiaColors.Green.primary
        else CineManiaColors.Orange.primary

    Box(
        modifier = Modifier
            .padding(top = 20.dp, end = 8.dp)
            .height(24.dp)
            .width(36.dp)
            .align(Alignment.TopEnd)
            .clip(MaterialTheme.shapes.extraSmall)
            .background(ratingBackground)
            .zIndex(3f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun StackedCoilImage(
    tvShow: TvShow
) {
    CoilImage(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .clip(MaterialTheme.shapes.small)
            .alpha(.3f)
            .zIndex(0f),
        imagePath = tvShow.posterPath,
        imageSize = NetworkImageSizes.MEDIUM
    )

    CoilImage(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 6.dp, end = 6.dp, top = 6.dp)
            .clip(MaterialTheme.shapes.small)
            .alpha(.5f)
            .zIndex(1f),
        imagePath = tvShow.posterPath,
        imageSize = NetworkImageSizes.MEDIUM
    )

    CoilImage(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
            .clip(MaterialTheme.shapes.small)
            .zIndex(2f),
        imagePath = tvShow.posterPath,
        imageSize = NetworkImageSizes.MEDIUM
    )
}
