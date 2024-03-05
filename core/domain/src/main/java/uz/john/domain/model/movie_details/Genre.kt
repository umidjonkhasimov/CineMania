package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.GenreData

data class Genre(
    val id: Int,
    val name: String
)

fun GenreData.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}