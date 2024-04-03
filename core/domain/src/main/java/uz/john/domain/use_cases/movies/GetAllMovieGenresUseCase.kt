package uz.john.domain.use_cases.movies

import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.common.Genre
import uz.john.domain.model.common.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetAllMovieGenresUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): ResultModel<List<Genre>> {
        val response = moviesRepository.getAllGenres()
        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                ResultModel.Success(response.data.genres.map { it.toDomain() })
            }
        }
    }
}