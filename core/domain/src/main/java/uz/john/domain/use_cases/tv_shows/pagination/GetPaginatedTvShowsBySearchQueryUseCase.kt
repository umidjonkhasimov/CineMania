package uz.john.domain.use_cases.tv_shows.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.john.data.pagination.tv_shows.TvShowsBySearchQueryPagingSource
import uz.john.data.repository.TvShowsRepository
import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.model.tv_show.toDomain
import javax.inject.Inject

class GetPaginatedTvShowsBySearchQueryUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    operator fun invoke(query: String): Flow<PagingData<TvShow>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            TvShowsBySearchQueryPagingSource(
                tvShowsRepository = tvShowsRepository,
                query = query
            )
        }
    ).flow.map { pagingData ->
        pagingData.map {
            it.toDomain()
        }
    }
}