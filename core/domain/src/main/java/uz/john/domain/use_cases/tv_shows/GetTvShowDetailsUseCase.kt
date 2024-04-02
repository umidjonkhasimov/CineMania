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
                val tvShowDetails = response.data.toDomain()
                val accountStateResponse = tvShowsRepository.getAccountStatesOfTvShow(tvShowDetails.id)

                when (accountStateResponse) {
                    is ResultModel.Error -> {
                        ResultModel.Error(accountStateResponse.error)
                    }

                    is ResultModel.Exception -> {
                        ResultModel.Exception(accountStateResponse.throwable)
                    }

                    is ResultModel.Success -> {
                        ResultModel.Success(
                            tvShowDetails.copy(
                                isFavorite = accountStateResponse.data.favorite,
                                isWatchLater = accountStateResponse.data.watchlist
                            )
                        )
                    }
                }
            }
        }
    }
}