package uz.john.domain.use_cases.movies

import uz.john.data.repository.MoviesRepository
import uz.john.util.ResultModel
import javax.inject.Inject

class SetMovieFavoriteUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, setFavorite: Boolean): ResultModel<Unit> {
        val response = moviesRepository.setMovieFavorite(movieId = movieId, setFavorite = setFavorite)

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