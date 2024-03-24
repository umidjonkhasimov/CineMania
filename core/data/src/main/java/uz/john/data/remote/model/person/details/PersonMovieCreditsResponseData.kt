package uz.john.data.remote.model.person.details

data class PersonMovieCreditsResponseData(
    val cast: List<CastCreditData>,
    val crew: List<CrewCreditData>
)