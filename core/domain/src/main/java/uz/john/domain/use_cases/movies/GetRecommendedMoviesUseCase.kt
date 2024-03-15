package uz.john.domain.use_cases.movies

import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.movie.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetRecommendedMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): ResultModel<List<Movie>> {
        val response = moviesRepository.getRecommendedMovies(movieId, page)
        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                val list = response.data.results.map { it.toDomain() }
                ResultModel.Success(list)
            }
        }
    }
}