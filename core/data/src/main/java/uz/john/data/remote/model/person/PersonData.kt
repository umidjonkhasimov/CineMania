package uz.john.data.remote.model.person

import com.google.gson.annotations.SerializedName

data class PersonData(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerializedName(value = "known_for")
    val knownForData: List<KnownForData>,
    @SerializedName(value = "known_for_department")
    val knownForDepartment: String?,
    val name: String,
    @SerializedName(value = "original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName(value = "profile_path")
    val profilePath: String?
)