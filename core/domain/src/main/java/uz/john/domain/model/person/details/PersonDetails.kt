package uz.john.domain.model.person.details

import uz.john.data.remote.model.person.details.PersonDetailsData
import uz.john.util.formatDate

data class PersonDetails(
    val adult: Boolean,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String?,
    val deathDay: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String?,
    val popularity: Double,
    val profilePath: String?,
    val movieCredits: PersonMovieCredits,
    val images: PersonImagesResponse
)

fun PersonDetailsData.toDomain(): PersonDetails {
    return PersonDetails(
        adult = adult,
        alsoKnownAs = alsoKnownAs,
        biography = biography,
        birthday = birthday?.formatDate(),
        deathDay = deathDay?.formatDate(),
        gender = gender,
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        knownForDepartment = knownForDepartment,
        name = name,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = profilePath,
        movieCredits = movieCredits.toDomain(),
        images = images.toDomain()
    )
}