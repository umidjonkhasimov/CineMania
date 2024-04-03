package uz.john.domain.use_cases.tv_shows

import uz.john.data.repository.TvShowsRepository
import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.model.tv_show.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetTrendingTodayTvShowsUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    suspend operator fun invoke(page: Int): ResultModel<List<TvShow>> {
        val result = tvShowsRepository.getTrendingTodayTvShows(page)
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