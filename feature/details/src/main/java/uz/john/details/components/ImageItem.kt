package uz.john.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.common.Image
import uz.john.ui.components.CoilImage

@Composable
fun ImageItem(
    image: Image,
    onImageClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(2.5f / 1.5f)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                image.filePath?.let {
                    onImageClick(it)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        CoilImage(
            modifier = Modifier.fillMaxSize(),
            imagePath = image.filePath,
            imageSize = NetworkImageSizes.MEDIUM
        )
    }
}