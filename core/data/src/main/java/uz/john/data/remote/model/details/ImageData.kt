package uz.john.data.remote.model.details

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName(value = "aspect_ratio")
    val aspectRatio: Double,
    @SerializedName(value = "file_path")
    val filePath: String?,
    val height: Int,
    @SerializedName(value = "iso_639_1")
    val languageCode: String,
    @SerializedName(value = "vote_average")
    val voteAverage: Double,
    @SerializedName(value = "vote_count")
    val voteCount: Int,
    val width: Int
)