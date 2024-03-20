package uz.john.domain.model.tv_show.tv_show_details

import uz.john.data.remote.model.tv_show.tv_show_details.SeasonData

data class Season(
    val airDate: String?,
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasonNumber: Int,
    val voteAverage: Double
)

fun SeasonData.toDomain(): Season {
    return Season(
        airDate = airDate,
        episodeCount = episodeCount,
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}