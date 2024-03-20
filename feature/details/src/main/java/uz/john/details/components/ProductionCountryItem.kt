package uz.john.details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.john.domain.model.common.ProductionCountry

@Composable
fun ProductionCountryItem(
    productionCountry: ProductionCountry
) {
    Box(
        modifier = Modifier
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f)
                ),
                RoundedCornerShape(6.dp)
            )
            .width(32.dp)
            .height(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = productionCountry.countryCode,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f)
        )
    }
}

@Preview
@Composable
private fun ProductionCountryItemPreview() {
    ProductionCountryItem(
        ProductionCountry("US", "")
    )
}