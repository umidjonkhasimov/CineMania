package uz.john.data.remote.model.movie.movie_details

import com.google.gson.annotations.SerializedName

data class ProductionCountryData(
    @SerializedName(value = "iso_3166_1")
    val countryCode: String,
    val name: String
)