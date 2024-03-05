package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.CastData

data class Cast(
    val castId: Int,
    val character: String,
    val creditId: String,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val profilePath: String?
)

fun CastData.toDomain(): Cast {
    return Cast(
        castId = castId,
        character = character,
        creditId = creditId,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        profilePath = profilePath
    )
}