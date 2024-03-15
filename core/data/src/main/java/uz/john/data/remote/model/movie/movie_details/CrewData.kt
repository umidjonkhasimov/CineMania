package uz.john.data.remote.model.movie.movie_details

import com.google.gson.annotations.SerializedName

data class CrewData(
    @SerializedName(value = "credit_id")
    val creditId: String,
    val department: String,
    val id: Int,
    val job: String,
    @SerializedName(value = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName(value = "original_name")
    val originalName: String,
    @SerializedName(value = "profile_path")
    val profilePath: String?
)