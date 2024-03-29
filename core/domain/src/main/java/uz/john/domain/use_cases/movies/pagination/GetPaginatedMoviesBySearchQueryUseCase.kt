package uz.john.domain.use_cases.movies.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.flow.map
import uz.john.data.pagination.movies.MoviesBySearchQueryPagingSource
import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.movie.toDomain
import javax.inject.Inject

class GetPaginatedMoviesBySearchQueryUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            MoviesBySearchQueryPagingSource(
                moviesRepository = moviesRepository,
                query = query
            )
        }
    ).flow.map { pagingData ->
        pagingData.map {
            it.toDomain()
        }
    }
}
