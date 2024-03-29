package uz.john.domain.model.profile

import uz.john.data.remote.model.authentication.get.AvatarData

data class Avatar(
    val tmdbAvatar: TmdbAvatar
)

fun AvatarData.toDomain(): Avatar {
    return Avatar(tmdbAvatar = tmdbAvatarData.toDomain())
}