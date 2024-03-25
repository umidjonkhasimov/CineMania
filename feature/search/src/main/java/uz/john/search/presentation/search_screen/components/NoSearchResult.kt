package uz.john.search.presentation.search_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.john.search.R
import uz.john.ui.theme.CineManiaIcons

@Composable
fun NoSearchResult() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            painter = painterResource(CineManiaIcons.Search),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = .3f)
        )

        Text(
            text = stringResource(R.string.nothing_found),
            style = MaterialTheme.typography.titleMedium
        )
    }
}