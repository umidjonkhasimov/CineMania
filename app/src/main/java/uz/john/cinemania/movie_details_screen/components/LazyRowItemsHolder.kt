package uz.john.cinemania.movie_details_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

private val HOLDER_HEIGHT = 300.dp

@Composable
fun LazyRowItemsHolder(
    modifier: Modifier = Modifier,
    title: String,
    itemsCount: Int,
    onSeeAllClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(HOLDER_HEIGHT)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = itemsCount.toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable { onSeeAllClick() }
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        content()
    }
}