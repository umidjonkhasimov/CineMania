package uz.john.domain.model.person.details

import uz.john.data.remote.model.person.details.PersonMovieCreditsResponseData

data class PersonMovieCredits(
    val cast: List<MovieCastCredit>,
    val crew: List<MovieCrewCredit>
)

fun PersonMovieCreditsResponseData.toDomain(): PersonMovieCredits {
    return PersonMovieCredits(
        cast = cast.map { it.toDomain() },
        crew = crew.map { it.toDomain() }
    )
}