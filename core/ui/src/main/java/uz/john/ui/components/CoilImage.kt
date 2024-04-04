package uz.john.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import uz.john.ui.BuildConfig
import uz.john.ui.R

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

@Composable
fun CoilImage(
    imagePath: String?,
    imageSize: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier.fillMaxSize(),
        model = "$IMAGE_BASE_URL$imageSize$imagePath",
        error = painterResource(R.drawable.ic_image_not_found),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
fun CoilVideoThumbnail(
    videoId: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier.fillMaxSize(),
        model = constructThumbnailUrl(videoId),
        error = painterResource(R.drawable.ic_image_not_found),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

private fun constructThumbnailUrl(videoId: String): String {
    return "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"
}