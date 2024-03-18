package uz.john.domain.model.person.details

import uz.john.data.remote.model.person.details.PersonImagesResponseData

data class PersonImagesResponse(
    val profiles: List<Profile>
)

fun PersonImagesResponseData.toDomain(): PersonImagesResponse {
    return PersonImagesResponse(
        profiles = profiles.map { it.toDomain() }
    )
}