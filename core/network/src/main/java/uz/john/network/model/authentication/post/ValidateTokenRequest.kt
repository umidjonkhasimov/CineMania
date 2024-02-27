package uz.john.network.model.authentication.post

import com.google.gson.annotations.SerializedName

data class ValidateTokenRequest(
    @SerializedName(value = "request_token")
    val requestToken: String,
    val username: String,
    val password: String
)