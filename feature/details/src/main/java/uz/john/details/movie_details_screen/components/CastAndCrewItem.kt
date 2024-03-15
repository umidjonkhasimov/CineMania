package uz.john.details.movie_details_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.movie.movie_details.Cast
import uz.john.domain.model.movie.movie_details.Crew
import uz.john.ui.components.CoilImage

private val ITEM_WIDTH = 250.dp

@Composable
fun CastItem(
    cast: Cast,
    onPersonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(end = 16.dp)
            .width(ITEM_WIDTH)
            .clickable {
                onPersonClick(cast.id)
            }
    ) {
        CoilImage(
            modifier = Modifier
                .aspectRatio(3f / 4f)
                .clip(MaterialTheme.shapes.extraSmall),
            imagePath = cast.profilePath,
            imageSize = NetworkImageSizes.MEDIUM
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = cast.originalName,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cast.character,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CrewItem(
    crew: Crew,
    onPersonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(end = 16.dp)
            .width(ITEM_WIDTH)
            .clickable {
                onPersonClick(crew.id)
            }
    ) {
        CoilImage(
            modifier = Modifier
                .aspectRatio(3f / 4f)
                .clip(MaterialTheme.shapes.extraSmall),
            imagePath = crew.profilePath,
            imageSize = NetworkImageSizes.MEDIUM
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = crew.originalName,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = crew.job,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}