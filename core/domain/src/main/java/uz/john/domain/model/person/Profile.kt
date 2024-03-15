package uz.john.domain.model.person

import uz.john.data.remote.model.person.ProfileData

data class Profile(
    val aspectRatio: Double,
    val filePath: String?,
    val height: Int,
    val languageCode: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int
)

fun ProfileData.toDomain(): Profile {
    return Profile(
        aspectRatio = aspectRatio,
        filePath = filePath,
        height = height,
        languageCode = languageCode,
        voteAverage = voteAverage,
        voteCount = voteCount,
        width = width
    )
}