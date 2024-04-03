package uz.john.domain.model.tv_show

import uz.john.data.remote.model.tv_show.TvShowData
import uz.john.util.formatDate
import uz.john.util.roundToOneDecimal

data class TvShow(
    val adult: Boolean,
    val backdropPath: String?,
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

fun TvShowData.toDomain(): TvShow {
    return TvShow(
        adult = adult,
        backdropPath = backdropPath,
        firstAirDate = firstAirDate.formatDate(),
        genreIds = genreIds,
        id = id,
        name = name,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity.roundToOneDecimal(),
        posterPath = posterPath,
        voteAverage = voteAverage.roundToOneDecimal(),
        voteCount = voteCount
    )
}