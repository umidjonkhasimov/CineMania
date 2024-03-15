package uz.john.domain.model.movie.movie_details

import uz.john.data.remote.model.movie.movie_details.ProductionCountryData

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