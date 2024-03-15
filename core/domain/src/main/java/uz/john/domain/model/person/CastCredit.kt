package uz.john.domain.model.person

import uz.john.data.remote.model.person.CastCreditData
import uz.john.util.formatDate
import uz.john.util.roundToOneDecimal

data class CastCredit(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val character: String,
    val creditId: String,
    val order: Int
)

fun CastCreditData.toDomain(): CastCredit {
    return CastCredit(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        mediaType = mediaType,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate.formatDate(),
        title = title,
        video = video,
        voteAverage = voteAverage.roundToOneDecimal(),
        voteCount = voteCount,
        character = character,
        creditId = creditId,
        order = order
    )
}