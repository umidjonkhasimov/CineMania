package uz.john.domain.use_cases

import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.Movie
import uz.john.domain.model.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(page: Int): ResultModel<List<Movie>> {
        val response = moviesRepository.getPopularMovies(page)

        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                val list = response.data.results.map {
                    it.toDomain()
                }

                ResultModel.Success(
                    list.take(10).shuffled()
                )
            }
        }
    }
}