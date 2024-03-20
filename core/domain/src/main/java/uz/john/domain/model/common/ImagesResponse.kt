package uz.john.domain.model.common

import uz.john.data.remote.model.common.ImagesResponseData

data class ImagesResponse(
    val backdrops: List<Image>,
    val logos: List<Image>,
    val posters: List<Image>
)

fun ImagesResponseData.toDomain(): ImagesResponse {
    return ImagesResponse(
        backdrops = backdrops.map { it.toDomain() },
        logos = logos.map { it.toDomain() },
        posters = posters.map { it.toDomain() }
    )
}