package uz.john.data.remote.model.movie.movie_details

import com.google.gson.annotations.SerializedName

data class ProductionCompanyData(
    val id: Int,
    @SerializedName(value = "logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName(value = "origin_country")
    val originCountry: String
)