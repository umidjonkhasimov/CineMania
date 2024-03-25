package uz.john.data.remote.model.person.details

data class PersonTvShowCreditsResponseData(
    val cast: List<TvShowCastCreditData>,
    val crew: List<TvShowCrewCreditData>
)