package uz.john.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.john.domain.model.common.Genre
import uz.john.ui.R
import uz.john.ui.theme.CineManiaIcons

@Composable
fun MediaGenreItem(
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
        Image(
            modifier = Modifier.alpha(.5f),
            painter = painterResource(R.drawable.genre_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(getIconByGenreId(genre.id)),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = genre.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun getIconByGenreId(id: Int): Int {
    return when (id) {
        // Movie
        28 -> CineManiaIcons.Action
        12 -> CineManiaIcons.Adventure
        16 -> CineManiaIcons.Animation
        35 -> CineManiaIcons.Comedy
        80 -> CineManiaIcons.Crime
        99 -> CineManiaIcons.Documentary
        18 -> CineManiaIcons.Drama
        10751 -> CineManiaIcons.Family
        14 -> CineManiaIcons.Fantasy
        36 -> CineManiaIcons.History
        27 -> CineManiaIcons.Horror
        10402 -> CineManiaIcons.Music
        9648 -> CineManiaIcons.Mystery
        10749 -> CineManiaIcons.Romance
        878 -> CineManiaIcons.SciFi
        10770 -> CineManiaIcons.TvMovie
        53 -> CineManiaIcons.Thriller
        10752 -> CineManiaIcons.War
        37 -> CineManiaIcons.Western

        // Tv Show
        10759 -> CineManiaIcons.Action
        10762 -> CineManiaIcons.Kid
        10763 -> CineManiaIcons.News
        10764 -> CineManiaIcons.Reality
        10765 -> CineManiaIcons.SciFi
        10766 -> CineManiaIcons.Soap
        10767 -> CineManiaIcons.Talk
        10768 -> CineManiaIcons.War

        else -> CineManiaIcons.Action
    }
}
