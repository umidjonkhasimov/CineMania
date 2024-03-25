package uz.john.data.remote.model.person.details

import com.google.gson.annotations.SerializedName

data class TvShowCastCreditData(
    val adult: Boolean,
    @SerializedName(value = "backdrop_path")
    val backdropPath: String?,
    val character: String,
    @SerializedName(value = "credit_id")
    val creditId: String,
    @SerializedName(value = "episode_count")
    val episodeCount: Int,
    @SerializedName(value = "first_air_date")
    val firstAirDate: String,
    @SerializedName(value = "genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    val name: String,
    @SerializedName(value = "origin_country")
    val originCountry: List<String>,
    @SerializedName(value = "original_language")
    val originalLanguage: String,
    @SerializedName(value = "original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName(value = "poster_path")
    val posterPath: String?,
    @SerializedName(value = "vote_average")
    val voteAverage: Double,
    @SerializedName(value = "vote_count")
    val voteCount: Int
)