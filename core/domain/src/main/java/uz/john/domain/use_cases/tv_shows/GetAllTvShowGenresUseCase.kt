package uz.john.domain.use_cases.tv_shows

import uz.john.data.repository.TvShowsRepository
import uz.john.domain.model.common.Genre
import uz.john.domain.model.common.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetAllTvShowGenresUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    suspend operator fun invoke(): ResultModel<List<Genre>> {
        val response = tvShowsRepository.getAllGenres()
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