package uz.john.data.remote.model.tv_show.tv_show_details

data class TvShowCreditsData(
    val cast: List<TvShowCastData>,
    val crew: List<TvShowCrewData>
)