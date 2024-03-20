package uz.john.domain.model.tv_show.tv_show_details

import uz.john.data.remote.model.tv_show.tv_show_details.TvShowCreditsData

data class TvShowCredits(
    val cast: List<TvShowCast>,
    val crew: List<TvShowCrew>
)

fun TvShowCreditsData.toDomain(): TvShowCredits {
    return TvShowCredits(
        cast = cast.map { it.toDomain() },
        crew = crew.map { it.toDomain() }
    )
}