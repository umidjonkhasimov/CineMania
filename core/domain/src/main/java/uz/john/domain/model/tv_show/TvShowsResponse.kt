package uz.john.domain.model.tv_show

import uz.john.data.remote.model.tv_show.TvShowData
import uz.john.data.remote.model.tv_show.TvShowsResponseData

data class TvShowsResponse(
    val page: Int,
    val results: List<TvShowData>,
    val totalPages: Int,
    val totalResults: Int
)

fun TvShowsResponseData.toDomain(): TvShowsResponse {
    return TvShowsResponse(
        page = page,
        results = results,
        totalPages = totalPages,
        totalResults = totalResults
    )
}