package uz.john.data.remote.model.authentication.get

import com.google.gson.annotations.SerializedName

data class CreateSessionResponse(
    @SerializedName(value = "session_id")
    val sessionId: String
)