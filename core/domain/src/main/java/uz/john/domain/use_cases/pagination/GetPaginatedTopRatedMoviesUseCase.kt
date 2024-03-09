package uz.john.domain.use_cases.pagination

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.Movie
import uz.john.domain.model.toDomain
import javax.inject.Inject

class GetPaginatedTopRatedMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesRepository.getPaginatedTopRatedMovies().map {
            it.map { movieData ->
                movieData.toDomain()
            }
        }
    }
}