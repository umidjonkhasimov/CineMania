package uz.john.data.remote.model.tv_show.tv_show_details

import com.google.gson.annotations.SerializedName
import uz.john.data.remote.model.common.GenreData
import uz.john.data.remote.model.common.ProductionCompanyData
import uz.john.data.remote.model.common.ProductionCountryData
import uz.john.data.remote.model.common.SpokenLanguageData
import uz.john.data.remote.model.common.ImagesResponseData
import uz.john.data.remote.model.common.VideosResponseData

data class TvShowDetailsData(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("created_by")
    val createdBy: List<CreatedByData>,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    val genres: List<GenreData>,
    val homepage: String,
    val id: Int,
    @SerializedName("in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @SerializedName("last_air_date")
    val lastAirDate: String?,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: EpisodeData?,
    val name: String,
    val networks: List<NetworkData>,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: EpisodeData?,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyData>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryData>,
    val seasons: List<SeasonData>,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageData>,
    val status: String,
    val tagline: String,
    val type: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    val images: ImagesResponseData,
    val videos: VideosResponseData,
    val credits: TvShowCreditsData
)