package uz.john.domain.model.tv_show.tv_show_details

import uz.john.data.remote.model.tv_show.tv_show_details.EpisodeData
import uz.john.util.formatDate

data class Episode(
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val id: Int,
    val name: String,
    val overview: String,
    val productionCode: String,
    val runtime: Int,
    val seasonNumber: Int,
    val showId: Int,
    val stillPath: String?,
    val voteAverage: Double,
    val voteCount: Int
)

fun EpisodeData.toDomain(): Episode {
    return Episode(
        airDate = airDate.formatDate(),
        episodeNumber = episodeNumber,
        episodeType = episodeType,
        id = id,
        name = name,
        overview = overview,
        productionCode = productionCode,
        runtime = runtime,
        seasonNumber = seasonNumber,
        showId = showId,
        stillPath = stillPath,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}