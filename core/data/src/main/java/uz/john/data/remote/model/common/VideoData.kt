package uz.john.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class VideoData(
    val id: String,
    @SerializedName(value = "iso_3166_1")
    val countryCode: String,
    @SerializedName(value = "iso_639_1")
    val languageCode: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @SerializedName(value = "published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)