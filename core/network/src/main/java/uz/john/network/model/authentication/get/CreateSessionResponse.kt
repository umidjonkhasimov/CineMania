package uz.john.network.model.authentication.get

import com.google.gson.annotations.SerializedName

data class CreateSessionResponse(
    @SerializedName(value = "session_id")
    val sessionId: String
)