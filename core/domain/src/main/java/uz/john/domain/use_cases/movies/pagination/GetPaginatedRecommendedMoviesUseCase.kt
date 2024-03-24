package uz.john.domain.use_cases.movies.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.john.data.pagination.movies.RecommendedMoviesPagingSource
import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.movie.toDomain
import javax.inject.Inject

class GetPaginatedRecommendedMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(movieId: Int): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            RecommendedMoviesPagingSource(
                moviesRepository = moviesRepository,
                movieId = movieId
            )
        }
    ).flow.map { pagingData ->
        pagingData.map {
            it.toDomain()
        }
    }
}