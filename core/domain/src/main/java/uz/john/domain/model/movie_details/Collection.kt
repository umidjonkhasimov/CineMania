package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.CollectionData

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