package uz.john.domain.model.person

import uz.john.data.remote.model.person.PersonCreditsResponseData

data class PersonCredits(
    val cast: List<CastCredit>,
    val crew: List<CrewCredit>
)

fun PersonCreditsResponseData.toDomain(): PersonCredits {
    return PersonCredits(
        cast = cast.map { it.toDomain() },
        crew = crew.map { it.toDomain() }
    )
}