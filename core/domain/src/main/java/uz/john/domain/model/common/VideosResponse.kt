package uz.john.domain.model.common

import uz.john.data.remote.model.common.VideosResponseData

data class VideosResponse(
    val videoList: List<Video>
)

fun VideosResponseData.toDomain(): VideosResponse {
    return VideosResponse(
        videoList = results.map { it.toDomain() }
    )
}