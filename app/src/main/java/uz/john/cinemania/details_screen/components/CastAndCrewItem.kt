package uz.john.cinemania.details_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.movie_details.Cast
import uz.john.domain.model.movie_details.Crew
import uz.john.ui.components.CoilImage

private val IMAGE_HEIGHT = 70.dp
private val IMAGE_WIDTH = 50.dp

@Composable
fun CastItem(
    cast: Cast,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(end = 24.dp)
    ) {
        CoilImage(
            modifier = Modifier
                .height(IMAGE_HEIGHT)
                .width(IMAGE_WIDTH)
                .clip(MaterialTheme.shapes.extraSmall),
            imagePath = cast.profilePath,
            imageSize = NetworkImageSizes.MEDIUM
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = cast.originalName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cast.character,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f)
            )
        }
    }
}

@Composable
fun CrewItem(
    crew: Crew,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(end = 24.dp)
    ) {
        CoilImage(
            modifier = Modifier
                .height(IMAGE_HEIGHT)
                .width(IMAGE_WIDTH)
                .clip(MaterialTheme.shapes.extraSmall),
            imagePath = crew.profilePath,
            imageSize = NetworkImageSizes.MEDIUM
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = crew.originalName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = crew.job,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f)
            )
        }
    }
}