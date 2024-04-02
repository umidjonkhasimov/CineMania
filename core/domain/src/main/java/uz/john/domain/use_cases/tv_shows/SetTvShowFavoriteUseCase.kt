package uz.john.domain.use_cases.tv_shows

import uz.john.data.repository.TvShowsRepository
import uz.john.util.ResultModel
import javax.inject.Inject

class SetTvShowFavoriteUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    suspend operator fun invoke(tvShowId: Int, setFavorite: Boolean): ResultModel<Unit> {
        val response = tvShowsRepository.setTvShowFavorite(tvShowId = tvShowId, setFavorite = setFavorite)

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