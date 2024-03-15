package uz.john.domain.model.person

import uz.john.data.remote.model.person.PersonImagesResponseData

data class PersonImagesResponse(
    val profiles: List<Profile>
)

fun PersonImagesResponseData.toDomain(): PersonImagesResponse {
    return PersonImagesResponse(
        profiles = profiles.map { it.toDomain() }
    )
}