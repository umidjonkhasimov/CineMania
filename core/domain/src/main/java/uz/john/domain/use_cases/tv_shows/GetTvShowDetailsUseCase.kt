package uz.john.domain.use_cases.tv_shows

import uz.john.data.repository.TvShowsRepository
import uz.john.domain.model.tv_show.tv_show_details.TvShowDetails
import uz.john.domain.model.tv_show.tv_show_details.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    suspend operator fun invoke(seriesId: Int): ResultModel<TvShowDetails> {
        val response = tvShowsRepository.getTvShowDetails(seriesId = seriesId)
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