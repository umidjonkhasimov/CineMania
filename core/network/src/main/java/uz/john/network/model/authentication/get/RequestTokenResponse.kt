package uz.john.network.model.authentication.get

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    @SerializedName(value = "request_token")
    val requestToken: String
)