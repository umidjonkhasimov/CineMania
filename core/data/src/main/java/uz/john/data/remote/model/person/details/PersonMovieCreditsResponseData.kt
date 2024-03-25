package uz.john.data.remote.model.person.details

data class PersonMovieCreditsResponseData(
    val cast: List<MovieCastCreditData>,
    val crew: List<MovieCrewCreditData>
)