package uz.john.domain.model.tv_show.tv_show_details

import uz.john.data.remote.model.tv_show.tv_show_details.TvShowCrewData

data class TvShowCrew(
    val adult: Boolean,
    val creditId: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)

fun TvShowCrewData.toDomain(): TvShowCrew {
    return TvShowCrew(
        adult = adult,
        creditId = creditId,
        department = department,
        gender = gender,
        id = id,
        job = job,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath
    )
}