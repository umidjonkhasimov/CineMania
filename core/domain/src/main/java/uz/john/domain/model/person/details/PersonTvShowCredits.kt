package uz.john.domain.model.person.details

import uz.john.data.remote.model.person.details.PersonTvShowCreditsResponseData

data class PersonTvShowCredits(
    val cast: List<TvShowCastCredit>,
    val crew: List<TvShowCrewCredit>
)

fun PersonTvShowCreditsResponseData.toDomain(): PersonTvShowCredits {
    return PersonTvShowCredits(
        cast = cast.map { it.toDomain() },
        crew = crew.map { it.toDomain() }
    )
}