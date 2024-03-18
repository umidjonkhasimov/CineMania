package uz.john.domain.model.person

import uz.john.data.remote.model.person.PersonData

data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForData: List<KnownFor>,
    val knownForDepartment: String?,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)

fun PersonData.toDomain(): Person {
    return Person(
        adult = adult,
        gender = gender,
        id = id,
        knownForData = knownForData.map { it.toDomain() },
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath
    )
}