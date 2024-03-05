package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.MovieDetailsData
import uz.john.util.formatDate
import uz.john.util.roundToOneDecimal

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String,
    val collection: Collection?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val credits: Credits
)

fun MovieDetailsData.toDomain(): MovieDetails {
    return MovieDetails(
        adult = adult,
        backdropPath = backdropPath,
        collection = collectionData?.toDomain(),
        budget = budget,
        genres = genreData.map { it.toDomain() },
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCompanies = productionCompanies.map { it.toDomain() },
        productionCountries = productionCountries.map { it.toDomain() },
        releaseDate = releaseDate.formatDate(),
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguageData.map { it.toDomain() },
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage.roundToOneDecimal(),
        voteCount = voteCount,
        credits = credits.toDomain()
    )
}