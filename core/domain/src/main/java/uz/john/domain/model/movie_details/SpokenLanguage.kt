package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.SpokenLanguageData

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