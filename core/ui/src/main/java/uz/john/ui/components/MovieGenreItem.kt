package uz.john.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import uz.john.domain.model.movie.movie_details.Genre

@Composable
fun MovieGenreItem(
    genre: Genre,
    onGenreClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .aspectRatio(3f / 2f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onGenreClick(genre.id) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}