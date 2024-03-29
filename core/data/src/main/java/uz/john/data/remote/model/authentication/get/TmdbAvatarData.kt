package uz.john.data.remote.model.authentication.get

import com.google.gson.annotations.SerializedName

data class TmdbAvatarData(
    @SerializedName(value = "avatar_path")
    val avatarPath: String?
)