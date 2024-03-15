package uz.john.domain.model.movie.movie_details

import uz.john.data.remote.model.movie.movie_details.VideoData

data class Video(
    val id: String,
    val countryCode: String,
    val languageCode: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)

fun VideoData.toDomain(): Video {
    return Video(
        id = id,
        countryCode = countryCode,
        languageCode = languageCode,
        key = key,
        name = name,
        official = official,
        publishedAt = publishedAt,
        site = site,
        size = size,
        type = type
    )
}