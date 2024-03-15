package uz.john.domain.model.movie.movie_details

import uz.john.data.remote.model.movie.movie_details.CreditsData

data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>
)

fun CreditsData.toDomain(): Credits {
    return Credits(
        cast = cast.map { it.toDomain() },
        crew = crew.map { it.toDomain() }
    )
}