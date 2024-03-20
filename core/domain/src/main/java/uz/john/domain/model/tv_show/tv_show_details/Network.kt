package uz.john.domain.model.tv_show.tv_show_details

import uz.john.data.remote.model.tv_show.tv_show_details.NetworkData

data class Network(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)

fun NetworkData.toDomain(): Network {
    return Network(
        id = id,
        logoPath = logoPath,
        name = name,
        originCountry = originCountry
    )
}