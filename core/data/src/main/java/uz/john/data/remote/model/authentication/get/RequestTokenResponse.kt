package uz.john.data.remote.model.authentication.get

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    @SerializedName(value = "request_token")
    val requestToken: String
)