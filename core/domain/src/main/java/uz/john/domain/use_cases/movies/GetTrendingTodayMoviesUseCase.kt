package uz.john.domain.use_cases.movies

import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.movie.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetTrendingTodayMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(page: Int): ResultModel<List<Movie>> {
        val result = moviesRepository.getTrendingTodayMovies(page)
        return when (result) {
            is ResultModel.Error -> {
                ResultModel.Error(result.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(result.throwable)
            }

            is ResultModel.Success -> {
                val list = result.data.results.map { it.toDomain() }
                ResultModel.Success(list)
            }
        }
    }
}