package uz.john.domain.model.person.details

import uz.john.data.remote.model.person.details.TvShowCastCreditData
import uz.john.util.roundToOneDecimal

data class TvShowCastCredit(
    val adult: Boolean,
    val backdropPath: String?,
    val character: String,
    val creditId: String,
    val episodeCount: Int,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val id: Int,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int
)

fun TvShowCastCreditData.toDomain(): TvShowCastCredit {
    return TvShowCastCredit(
        adult = adult,
        backdropPath = backdropPath,
        character = character,
        creditId = creditId,
        episodeCount = episodeCount,
        firstAirDate = firstAirDate,
        genreIds = genreIds,
        id = id,
        name = name,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        voteAverage = voteAverage.roundToOneDecimal(),
        voteCount = voteCount
    )
}