package uz.john.domain.model.common

import uz.john.data.remote.model.common.GenreData

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