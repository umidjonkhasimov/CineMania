package uz.john.data.local.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferencesData(
    val isOnboarded: Boolean = false,
    val isLoggedIn: Boolean = false,
    val sessionId: String = "",
)