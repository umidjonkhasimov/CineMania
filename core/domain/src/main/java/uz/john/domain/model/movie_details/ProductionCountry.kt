package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.ProductionCountryData

data class ProductionCountry(
    val countryCode: String,
    val name: String
)

fun ProductionCountryData.toDomain(): ProductionCountry {
    return ProductionCountry(
        countryCode = countryCode,
        name = name
    )
}