package uz.john.domain.model.common

import uz.john.data.remote.model.common.SpokenLanguageData

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