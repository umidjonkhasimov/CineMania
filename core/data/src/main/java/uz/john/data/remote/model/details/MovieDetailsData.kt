package uz.john.data.remote.model.details

import com.google.gson.annotations.SerializedName

data class MovieDetailsData(
    val adult: Boolean,
    @SerializedName(value = "backdrop_path")
    val backdropPath: String?,
    @SerializedName(value = "belongs_to_collection")
    val collectionData: CollectionData?,
    val budget: Int,
    @SerializedName(value = "genres")
    val genreData: List<GenreData>,
    val homepage: String,
    val id: Int,
    @SerializedName(value = "imdb_id")
    val imdbId: String?,
    @SerializedName(value = "original_language")
    val originalLanguage: String,
    @SerializedName(value = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName(value = "poster_path")
    val posterPath: String?,
    @SerializedName(value = "production_companies")
    val productionCompanies: List<ProductionCompanyData>,
    @SerializedName(value = "production_countries")
    val productionCountries: List<ProductionCountryData>,
    @SerializedName(value = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @SerializedName(value = "spoken_languages")
    val spokenLanguageData: List<SpokenLanguageData>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName(value = "vote_average")
    val voteAverage: Double,
    @SerializedName(value = "vote_count")
    val voteCount: Int,
    val credits: CreditsData,
    val videos: VideosResponseData
)