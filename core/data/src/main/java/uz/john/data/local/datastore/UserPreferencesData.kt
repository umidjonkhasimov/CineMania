package uz.john.data.local.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferencesData(
    val isOnboarded: Boolean = false,
    val isLoggedIn: Boolean = false,
    val sessionId: String = "",
    val includeAdult: Boolean = true,
    val uiMode: UiModeData = UiModeData.DARK,
    val languageData: LanguageData = LanguageData.ENGLISH
)

@Serializable
enum class UiModeData {
    SYSTEM,
    LIGHT,
    DARK
}

@Serializable
enum class LanguageData(val locale: String) {
    ENGLISH(locale = "en"),
    RUSSIAN(locale = "ru")
}