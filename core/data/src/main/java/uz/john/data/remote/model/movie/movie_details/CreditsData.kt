package uz.john.data.remote.model.movie.movie_details

data class CreditsData(
    val cast: List<CastData>,
    val crew: List<CrewData>
)