package uz.john.domain.model.common

import uz.john.data.remote.model.common.ProductionCountryData

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