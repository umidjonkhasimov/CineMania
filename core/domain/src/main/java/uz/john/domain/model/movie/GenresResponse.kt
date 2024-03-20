package uz.john.domain.model.movie

import uz.john.data.remote.model.movie.GenresResponseData
import uz.john.domain.model.common.Genre
import uz.john.domain.model.common.toDomain

data class GenresResponse(
    val genres: List<Genre>
)

fun GenresResponseData.toDomain(): GenresResponse {
    return GenresResponse(
        genres = genres.map { it.toDomain() }
    )
}