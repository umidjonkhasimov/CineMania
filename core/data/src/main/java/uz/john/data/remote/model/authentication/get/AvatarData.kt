package uz.john.data.remote.model.authentication.get

import com.google.gson.annotations.SerializedName

data class AvatarData(
    @SerializedName(value = "tmdb")
    val tmdbAvatarData: TmdbAvatarData
)