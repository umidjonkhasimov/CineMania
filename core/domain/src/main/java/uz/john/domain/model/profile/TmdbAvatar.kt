package uz.john.domain.model.profile

import uz.john.data.remote.model.authentication.get.TmdbAvatarData

data class TmdbAvatar(
    val avatarPath: String?
)

fun TmdbAvatarData.toDomain(): TmdbAvatar {
    return TmdbAvatar(avatarPath = avatarPath)
}