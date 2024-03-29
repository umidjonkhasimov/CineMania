package uz.john.paginated_movies_list.all_movies_screen

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import uz.john.domain.model.movie.Movie
import uz.john.domain.use_cases.movies.pagination.GetPaginatedMoviesByGenreUseCase
import uz.john.domain.use_cases.movies.pagination.GetPaginatedMoviesBySearchQueryUseCase
import uz.john.domain.use_cases.movies.pagination.GetPaginatedMoviesTrendingThisWeekUseCase
import uz.john.domain.use_cases.movies.pagination.GetPaginatedNowPlayingMoviesUseCase
import uz.john.domain.use_cases.movies.pagination.GetPaginatedPopularMoviesUseCase
import uz.john.domain.use_cases.movies.pagination.GetPaginatedRecommendedMoviesUseCase
import uz.john.domain.use_cases.movies.pagination.GetPaginatedSimilarMoviesUseCase
import uz.john.domain.use_cases.movies.pagination.GetPaginatedTopRatedMoviesUseCase
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.SideEffect
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.UiAction
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    getPaginatedNowPlayingMoviesUseCase: GetPaginatedNowPlayingMoviesUseCase,
    getPaginatedPopularMoviesUseCase: GetPaginatedPopularMoviesUseCase,
    getPaginatedTopRatedMoviesUseCase: GetPaginatedTopRatedMoviesUseCase,
    getPaginatedSimilarMoviesUseCase: GetPaginatedSimilarMoviesUseCase,
    getPaginatedRecommendedMoviesUseCase: GetPaginatedRecommendedMoviesUseCase,
    getPaginatedMoviesByGenreUseCase: GetPaginatedMoviesByGenreUseCase,
    getPaginatedMoviesBySearchQueryUseCase: GetPaginatedMoviesBySearchQueryUseCase,
    getPaginatedMoviesTrendingThisWeekUseCase: GetPaginatedMoviesTrendingThisWeekUseCase,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val json = savedStateHandle.get<String>(ALL_MOVIES_ARG)

    private val allMoviesScreenParam = json?.let {
        Json.decodeFromString<AllMoviesScreenParam>(json)
    }

    init {
        allMoviesScreenParam?.let {
            updateUiState {
                copy(title = allMoviesScreenParam.getTitle(context))
            }
        }
    }

    var paginatedMovies: Flow<PagingData<Movie>> =
        when (allMoviesScreenParam) {
            AllMoviesScreenParam.NowPlayingMovies -> {
                getPaginatedNowPlayingMoviesUseCase().cachedIn(viewModelScope)
            }

            AllMoviesScreenParam.PopularMovies -> {
                getPaginatedPopularMoviesUseCase().cachedIn(viewModelScope)
            }

            is AllMoviesScreenParam.SimilarMovies -> {
                getPaginatedSimilarMoviesUseCase(allMoviesScreenParam.movieId).cachedIn(viewModelScope)
            }

            AllMoviesScreenParam.TopRated -> {
                getPaginatedTopRatedMoviesUseCase().cachedIn(viewModelScope)
            }

            is AllMoviesScreenParam.RecommendedMovies -> {
                getPaginatedRecommendedMoviesUseCase(allMoviesScreenParam.movieId).cachedIn(viewModelScope)
            }

            is AllMoviesScreenParam.AllMoviesByGenre -> {
                getPaginatedMoviesByGenreUseCase(allMoviesScreenParam.genreId).cachedIn(viewModelScope)
            }

            is AllMoviesScreenParam.AllMoviesBySearchQuery -> {
                getPaginatedMoviesBySearchQueryUseCase(allMoviesScreenParam.query).cachedIn(viewModelScope)
            }

            AllMoviesScreenParam.MoviesTrendingThisWeek -> {
                getPaginatedMoviesTrendingThisWeekUseCase().cachedIn(viewModelScope)
            }

            null -> {
                flowOf()
            }
        }
}