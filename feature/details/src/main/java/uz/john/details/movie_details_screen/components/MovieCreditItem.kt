package uz.john.details.movie_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.person.CastCredit
import uz.john.domain.model.person.CrewCredit
import uz.john.ui.components.CoilImage
import uz.john.ui.theme.CineManiaColors

@Composable
fun PersonCastCreditItem(
    movieData: CastCredit,
    onMovieItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rating = movieData.voteAverage

    val ratingBackground =
        if (rating < 7.0) CineManiaColors.Gray
        else if (rating < 8.0) CineManiaColors.Green.primary
        else CineManiaColors.Orange.primary

    Column(
        modifier = modifier
            .aspectRatio(ratio = 2f / 3f)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.small)
                .clickable {
                    onMovieItemClick(movieData.id)
                }
        ) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                imagePath = movieData.posterPath,
                imageSize = NetworkImageSizes.MEDIUM
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(24.dp)
                    .width(36.dp)
                    .align(Alignment.TopEnd)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(ratingBackground),
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

        Text(
            text = movieData.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = movieData.character,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PersonCrewCreditItem(
    movieData: CrewCredit,
    onMovieItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rating = movieData.voteAverage

    val ratingBackground =
        if (rating < 7.0) CineManiaColors.Gray
        else if (rating < 8.0) CineManiaColors.Green.primary
        else CineManiaColors.Orange.primary

    Column(
        modifier = modifier
            .aspectRatio(ratio = 2f / 3f)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.small)
                .clickable {
                    onMovieItemClick(movieData.id)
                }
        ) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                imagePath = movieData.posterPath,
                imageSize = NetworkImageSizes.MEDIUM
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(24.dp)
                    .width(36.dp)
                    .align(Alignment.TopEnd)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(ratingBackground),
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

        Text(
            text = movieData.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = movieData.job,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}