package uz.john.network.model.authentication.post

import com.google.gson.annotations.SerializedName

data class CreateSessionRequest(
    @SerializedName(value = "request_token")
    val requestToken: String
)