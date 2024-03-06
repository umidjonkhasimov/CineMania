package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.VideosResponseData

data class VideosResponse(
    val videoList: List<Video>
)

fun VideosResponseData.toDomain(): VideosResponse {
    return VideosResponse(
        videoList = results.map { it.toDomain() }
    )
}