package uz.john.domain.use_cases.tv_shows

import uz.john.data.repository.TvShowsRepository
import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.model.tv_show.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetFavoriteTvShowsUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    suspend operator fun invoke(): ResultModel<List<TvShow>> {
        val response = tvShowsRepository.getFavoriteTvShows(1)
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