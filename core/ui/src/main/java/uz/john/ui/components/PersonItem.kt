package uz.john.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.person.Person

@Composable
fun PersonItem(
    person: Person,
    onPersonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .aspectRatio(ratio = 2.5f / 3f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            modifier = Modifier
                .aspectRatio(1f / 1f)
                .weight(1f)
                .clip(MaterialTheme.shapes.extraLarge)
                .clickable {
                    onPersonClick(person.id)
                },
            imagePath = person.profilePath,
            imageSize = NetworkImageSizes.MEDIUM
        )
        Text(
            text = person.name,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun PersonItemPreview() {
    PersonItem(
        person = Person(
            adult = true,
            gender = 1,
            id = 1,
            knownForDepartment = "Acting",
            knownForData = listOf(),
            name = "Tom Cruise",
            originalName = "Tom Cruise",
            popularity = 1.0,
            profilePath = ""
        ),
        onPersonClick = {}
    )
}