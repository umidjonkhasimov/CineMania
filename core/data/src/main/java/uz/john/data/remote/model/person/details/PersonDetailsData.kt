package uz.john.data.remote.model.person.details

import com.google.gson.annotations.SerializedName

data class PersonDetailsData(
    val adult: Boolean,
    @SerializedName(value = "also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String?,
    @SerializedName(value = "deathday")
    val deathDay: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @SerializedName(value = "imdb_id")
    val imdbId: String?,
    @SerializedName(value = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName(value = "place_of_birth")
    val placeOfBirth: String?,
    val popularity: Double,
    @SerializedName(value = "profile_path")
    val profilePath: String?,
    val credits: PersonCreditsResponseData,
    val images: PersonImagesResponseData
)