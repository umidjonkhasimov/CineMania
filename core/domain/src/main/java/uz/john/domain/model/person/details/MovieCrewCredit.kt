package uz.john.domain.model.person.details

import uz.john.data.remote.model.person.details.MovieCrewCreditData
import uz.john.util.formatDate
import uz.john.util.roundToOneDecimal

data class MovieCrewCredit(
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
    val creditId: String,
    val department: String,
    val job: String
)

fun MovieCrewCreditData.toDomain(): MovieCrewCredit {
    return MovieCrewCredit(
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
        creditId = creditId,
        department = department,
        job = job
    )
}