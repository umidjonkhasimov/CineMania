package uz.john.domain.model.tv_show.tv_show_details

import uz.john.data.remote.model.tv_show.tv_show_details.CreatedByData

data class CreatedBy(
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val profilePath: String?
)

fun CreatedByData.toDomain(): CreatedBy {
    return CreatedBy(
        creditId = creditId,
        gender = gender,
        id = id,
        name = name,
        profilePath = profilePath
    )
}