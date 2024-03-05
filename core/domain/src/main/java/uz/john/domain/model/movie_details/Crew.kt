package uz.john.domain.model.movie_details

import uz.john.data.remote.model.details.CrewData

data class Crew(
    val creditId: String,
    val department: String,
    val id: Int,
    val job: String,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val profilePath: String?
)

fun CrewData.toDomain(): Crew {
    return Crew(
        creditId = creditId,
        department = department,
        id = id,
        job = job,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        profilePath = profilePath
    )
}