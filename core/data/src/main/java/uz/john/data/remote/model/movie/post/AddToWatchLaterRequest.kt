package uz.john.data.remote.model.movie.post

import com.google.gson.annotations.SerializedName

data class AddToWatchLaterRequest(
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_id")
    val mediaId: Int,
    val watchlist: Boolean
)