package uz.john.data.remote.model.movie.movie_details

import com.google.gson.annotations.SerializedName

data class SpokenLanguageData(
    @SerializedName(value = "english_name")
    val englishName: String,
    @SerializedName(value = "iso_639_1")
    val languageCode: String,
    val name: String
)