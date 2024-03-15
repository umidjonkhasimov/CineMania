package uz.john.domain.model.person

import uz.john.data.remote.model.person.PersonData
import uz.john.util.formatDate

data class Person(
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
    val credits: PersonCredits,
    val images: PersonImagesResponse
)

fun PersonData.toDomain(): Person {
    return Person(
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
        credits = credits.toDomain(),
        images = images.toDomain()
    )
}