package uz.john.domain.model

import uz.john.data.local.datastore.UserPreferencesData

data class UserPreferences(
    val isOnboarded: Boolean,
    val isLoggedIn: Boolean,
    val sessionId: String
)

fun UserPreferencesData.toDomain(): UserPreferences {
    return UserPreferences(
        isOnboarded = isOnboarded,
        isLoggedIn = isLoggedIn,
        sessionId = sessionId
    )
}