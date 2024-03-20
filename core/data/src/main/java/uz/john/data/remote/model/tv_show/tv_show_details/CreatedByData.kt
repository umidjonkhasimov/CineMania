package uz.john.data.remote.model.tv_show.tv_show_details

import com.google.gson.annotations.SerializedName

data class CreatedByData(
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?
)