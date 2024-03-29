package uz.john.domain.use_cases.movies.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.flow.map
import uz.john.data.pagination.movies.MoviesTrendingThisWeekPagingSource
import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.movie.toDomain
import javax.inject.Inject

class GetPaginatedMoviesTrendingThisWeekUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke() = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            MoviesTrendingThisWeekPagingSource(
                moviesRepository = moviesRepository
            )
        }
    ).flow.map { pagingData ->
        pagingData.map {
            it.toDomain()
        }
    }
}
