package uz.john.domain.model.profile

import uz.john.data.remote.model.authentication.get.UserAccountDetailsData

data class UserAccountDetails(
    val avatar: Avatar,
    val id: Int,
    val includeAdult: Boolean,
    val languageCode: String,
    val regionCode: String,
    val name: String,
    val username: String
)

fun UserAccountDetailsData.toDomain(): UserAccountDetails {
    return UserAccountDetails(
        avatar = avatar.toDomain(),
        id = id,
        includeAdult = includeAdult,
        languageCode = languageCode,
        regionCode = regionCode,
        name = name,
        username = username
    )
}