package uz.john.domain.model

data class UserData(
    val isOnboarded: Boolean,
    val isLoggedIn: Boolean,
    val sessionId: String
)