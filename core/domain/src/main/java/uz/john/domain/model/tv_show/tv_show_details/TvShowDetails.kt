package uz.john.domain.model.tv_show.tv_show_details

import uz.john.data.remote.model.tv_show.tv_show_details.TvShowDetailsData
import uz.john.domain.model.common.Genre
import uz.john.domain.model.common.ImagesResponse
import uz.john.domain.model.common.ProductionCompany
import uz.john.domain.model.common.ProductionCountry
import uz.john.domain.model.common.SpokenLanguage
import uz.john.domain.model.common.VideosResponse
import uz.john.domain.model.common.toDomain
import uz.john.util.formatDate
import uz.john.util.roundToOneDecimal

data class TvShowDetails(
    val adult: Boolean,
    val backdropPath: String?,
    val createdBy: List<CreatedBy>,
    val episodeRunTime: List<Int>,
    val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String?,
    val lastEpisodeToAir: Episode?,
    val name: String,
    val networks: List<Network>,
    val nextEpisodeToAir: Episode?,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Int,
    val images: ImagesResponse,
    val videos: VideosResponse,
    val credits: TvShowCredits
)

fun TvShowDetailsData.toDomain(): TvShowDetails {
    return TvShowDetails(
        adult = adult,
        backdropPath = backdropPath,
        createdBy = createdBy.map { it.toDomain() },
        episodeRunTime = episodeRunTime,
        firstAirDate = firstAirDate.formatDate(),
        genres = genres.map { it.toDomain() },
        homepage = homepage,
        id = id,
        inProduction = inProduction,
        languages = languages,
        lastAirDate = lastAirDate?.formatDate(),
        lastEpisodeToAir = lastEpisodeToAir?.toDomain(),
        name = name,
        networks = networks.map { it.toDomain() },
        nextEpisodeToAir = nextEpisodeToAir?.toDomain(),
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCompanies = productionCompanies.map { it.toDomain() },
        productionCountries = productionCountries.map { it.toDomain() },
        seasons = seasons.map { it.toDomain() },
        spokenLanguages = spokenLanguages.map { it.toDomain() },
        status = status,
        tagline = tagline,
        type = type,
        voteAverage = voteAverage.roundToOneDecimal(),
        voteCount = voteCount,
        images = images.toDomain(),
        videos = videos.toDomain(),
        credits = credits.toDomain()
    )
}