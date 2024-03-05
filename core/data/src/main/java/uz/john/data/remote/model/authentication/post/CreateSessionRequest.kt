package uz.john.data.remote.model.authentication.post

import com.google.gson.annotations.SerializedName

data class CreateSessionRequest(
    @SerializedName(value = "request_token")
    val requestToken: String
)