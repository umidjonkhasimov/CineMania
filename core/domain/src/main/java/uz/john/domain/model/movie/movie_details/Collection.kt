package uz.john.domain.model.movie.movie_details

import uz.john.data.remote.model.movie.movie_details.CollectionData

data class Collection(
    val backdropPath: String?,
    val id: Int,
    val name: String,
    val posterPath: String?
)

fun CollectionData.toDomain(): Collection {
    return Collection(
        backdropPath = backdropPath,
        id = id,
        name = name,
        posterPath = posterPath
    )
}