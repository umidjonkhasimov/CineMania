package uz.john.domain.model.person.details

import uz.john.data.remote.model.person.details.TvShowCrewCreditData
import uz.john.util.roundToOneDecimal

data class TvShowCrewCredit(
    val adult: Boolean,
    val backdropPath: String?,
    val creditId: String,
    val department: String,
    val episodeCount: Int,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val id: Int,
    val job: String,
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

fun TvShowCrewCreditData.toDomain(): TvShowCrewCredit {
    return TvShowCrewCredit(
        adult = adult,
        backdropPath = backdropPath,
        creditId = creditId,
        department = department,
        episodeCount = episodeCount,
        firstAirDate = firstAirDate,
        genreIds = genreIds,
        id = id,
        job = job,
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