package uz.john.profile.presentation.profile_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.john.ui.components.shimmerEffect

private val IMAGE_SIZE = 100.dp
private val SPACE_HEIGHT = 32.dp
private val BUTTON_HEIGHT = 56.dp

@Composable
fun ProfileScreenShimmerEffect(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(SPACE_HEIGHT))

        Box(
            modifier = Modifier
                .size(IMAGE_SIZE)
                .clip(MaterialTheme.shapes.extraLarge)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(32.dp)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(SPACE_HEIGHT))

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(32.dp)
                .clip(MaterialTheme.shapes.small)
                .align(Alignment.Start)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        for (i in 1..3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .height(BUTTON_HEIGHT)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(SPACE_HEIGHT))

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(32.dp)
                .clip(MaterialTheme.shapes.small)
                .align(Alignment.Start)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        for (i in 1..2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .height(BUTTON_HEIGHT)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}