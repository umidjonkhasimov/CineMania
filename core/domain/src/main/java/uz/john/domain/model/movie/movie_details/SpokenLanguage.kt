package uz.john.domain.model.movie.movie_details

import uz.john.data.remote.model.movie.movie_details.SpokenLanguageData

data class SpokenLanguage(
    val englishName: String,
    val languageCode: String,
    val name: String
)

fun SpokenLanguageData.toDomain(): SpokenLanguage {
    return SpokenLanguage(
        englishName = englishName,
        languageCode = languageCode,
        name = name
    )
}