package uz.john.domain.use_cases.movies

import uz.john.data.repository.MoviesRepository
import uz.john.util.ResultModel
import javax.inject.Inject

class SetMovieWatchLaterUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, setWatchLater: Boolean): ResultModel<Unit> {
        val response = moviesRepository.setMovieWatchLater(
            movieId = movieId,
            setWatchLater = setWatchLater
        )

        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                ResultModel.Success(Unit)
            }
        }
    }
}