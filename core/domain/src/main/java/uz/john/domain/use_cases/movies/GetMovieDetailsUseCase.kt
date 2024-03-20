package uz.john.domain.use_cases.movies

import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.movie.movie_details.MovieDetails
import uz.john.domain.model.common.toDomain
import uz.john.domain.model.movie.movie_details.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): ResultModel<MovieDetails> {
        val response = moviesRepository.getMovieDetails(movieId)

        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                ResultModel.Success(response.data.toDomain())
            }
        }
    }
}