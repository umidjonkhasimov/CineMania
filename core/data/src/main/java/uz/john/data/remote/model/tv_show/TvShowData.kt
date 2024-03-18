package uz.john.data.remote.model.tv_show

import com.google.gson.annotations.SerializedName

data class TvShowData(
    val adult: Boolean,
    @SerializedName(value = "backdrop_path")
    val backdropPath: String?,
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