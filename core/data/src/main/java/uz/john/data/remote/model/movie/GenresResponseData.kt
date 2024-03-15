package uz.john.data.remote.model.movie

import uz.john.data.remote.model.movie.movie_details.GenreData

data class GenresResponseData(
    val genres: List<GenreData>
)