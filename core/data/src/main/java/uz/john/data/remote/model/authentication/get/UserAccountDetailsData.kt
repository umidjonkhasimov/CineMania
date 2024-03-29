package uz.john.data.remote.model.authentication.get

import com.google.gson.annotations.SerializedName

data class UserAccountDetailsData(
    val avatar: AvatarData,
    val id: Int,
    @SerializedName(value = "include_adult")
    val includeAdult: Boolean,
    @SerializedName(value = "iso_3166_1")
    val languageCode: String,
    @SerializedName(value = "iso_639_1")
    val regionCode: String,
    val name: String,
    val username: String
)