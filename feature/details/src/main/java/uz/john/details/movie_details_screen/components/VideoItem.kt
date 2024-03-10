package uz.john.details.movie_details_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.john.domain.model.movie_details.Video
import uz.john.ui.components.CoilVideoThumbnail
import uz.john.ui.theme.CineManiaIcons

@Composable
fun VideoItem(
    video: Video,
    onVideoClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(2.5f / 1.5f)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onVideoClick(video.key)
            },
        contentAlignment = Alignment.Center
    ) {
        CoilVideoThumbnail(
            modifier = Modifier.fillMaxSize(),
            videoId = video.key
        )

        Icon(
            modifier = Modifier.size(56.dp),
            painter = painterResource(CineManiaIcons.YouTube),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}