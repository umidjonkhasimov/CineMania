package uz.john.data.remote.model.tv_show.tv_show_details

import com.google.gson.annotations.SerializedName

data class NetworkData(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)