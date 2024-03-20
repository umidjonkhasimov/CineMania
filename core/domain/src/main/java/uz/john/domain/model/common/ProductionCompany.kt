package uz.john.domain.model.common

import uz.john.data.remote.model.common.ProductionCompanyData


data class ProductionCompany(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)

fun ProductionCompanyData.toDomain(): ProductionCompany {
    return ProductionCompany(
        id = id,
        logoPath = logoPath,
        name = name,
        originCountry = originCountry
    )
}