package uz.john.domain.use_cases.tv_shows.pagination

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.john.data.repository.SearchRepository
import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.model.tv_show.toDomain
import javax.inject.Inject

class GetPaginatedTvShowsBySearchQueryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<TvShow>> {
        return searchRepository.getPaginatedTvShowsBySearchQuery(query).map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
    }
}