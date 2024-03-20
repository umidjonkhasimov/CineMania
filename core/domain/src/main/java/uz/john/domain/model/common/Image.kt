package uz.john.domain.model.common

import uz.john.data.remote.model.common.ImageData

data class Image(
    val aspectRatio: Double,
    val filePath: String?,
    val height: Int,
    val languageCode: String,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int
)

fun ImageData.toDomain(): Image {
    return Image(
        aspectRatio = aspectRatio,
        filePath = filePath,
        height = height,
        languageCode = languageCode,
        voteAverage = voteAverage,
        voteCount = voteCount,
        width = width
    )
}