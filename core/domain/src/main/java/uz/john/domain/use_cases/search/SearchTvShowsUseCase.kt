package uz.john.domain.use_cases.search

import uz.john.data.repository.SearchRepository
import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.model.tv_show.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class SearchTvShowsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, page: Int): ResultModel<List<TvShow>> {
        val response = searchRepository.searchTvShows(query = query, page = page)
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