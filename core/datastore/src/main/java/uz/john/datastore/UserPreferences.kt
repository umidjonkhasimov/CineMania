package uz.john.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val isOnboarded: Boolean = false,
    val isLoggedIn: Boolean = false,
    val sessionId: String = "",
)