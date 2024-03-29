package uz.john.domain.model

import uz.john.data.local.datastore.LanguageData
import uz.john.data.local.datastore.UiModeData
import uz.john.data.local.datastore.UserPreferencesData

data class UserPreferences(
    val isOnboarded: Boolean,
    val isLoggedIn: Boolean,
    val sessionId: String,
    val includeAdult: Boolean,
    val uiMode: UiMode,
    val language: Language
)

enum class UiMode {
    SYSTEM,
    LIGHT,
    DARK
}

enum class Language(val languageName: String, val locale: String) {
    ENGLISH(languageName = "English", locale = "en"),
    RUSSIAN(languageName = "Русский", locale = "ru")
}

fun UserPreferencesData.toDomain(): UserPreferences {
    return UserPreferences(
        isOnboarded = isOnboarded,
        isLoggedIn = isLoggedIn,
        sessionId = sessionId,
        includeAdult = includeAdult,
        uiMode = uiMode.toDomain(),
        language = languageData.toDomain()
    )
}

fun UiModeData.toDomain(): UiMode {
    return when (this) {
        UiModeData.SYSTEM -> UiMode.SYSTEM
        UiModeData.LIGHT -> UiMode.LIGHT
        UiModeData.DARK -> UiMode.DARK
    }
}

fun UiMode.toData(): UiModeData {
    return when (this) {
        UiMode.SYSTEM -> UiModeData.SYSTEM
        UiMode.LIGHT -> UiModeData.LIGHT
        UiMode.DARK -> UiModeData.DARK
    }
}

fun LanguageData.toDomain(): Language {
    return when (this) {
        LanguageData.ENGLISH -> Language.ENGLISH
        LanguageData.RUSSIAN -> Language.RUSSIAN
    }
}

fun Language.toData(): LanguageData {
    return when (this) {
        Language.ENGLISH -> LanguageData.ENGLISH
        Language.RUSSIAN -> LanguageData.RUSSIAN
    }
}