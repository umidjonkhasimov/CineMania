package uz.john.domain.model.movie.movie_details

import uz.john.data.remote.model.movie.movie_details.ImagesResponseData

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