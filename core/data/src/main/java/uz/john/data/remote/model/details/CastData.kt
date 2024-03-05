package uz.john.data.remote.model.details

import com.google.gson.annotations.SerializedName

data class CastData(
    @SerializedName(value = "cast_id")
    val castId: Int,
    val character: String,
    @SerializedName(value = "credit_id")
    val creditId: String,
    val id: Int,
    @SerializedName(value = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName(value = "original_name")
    val originalName: String,
    @SerializedName(value = "profile_path")
    val profilePath: String?
)