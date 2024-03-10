package uz.john.paginated_movies_list.all_movies_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import uz.john.domain.model.Movie
import uz.john.domain.use_cases.pagination.GetPaginatedPopularMoviesUseCase
import uz.john.domain.use_cases.pagination.GetPaginatedSimilarMoviesUseCase
import uz.john.domain.use_cases.pagination.GetPaginatedTopRatedMoviesUseCase
import uz.john.paginated_movies_list.AllMoviesMediaType
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.SideEffect
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.UiAction
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    getPaginatedPopularMoviesUseCase: GetPaginatedPopularMoviesUseCase,
    getPaginatedTopRatedMoviesUseCase: GetPaginatedTopRatedMoviesUseCase,
    getPaginatedSimilarMoviesUseCase: GetPaginatedSimilarMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val json = savedStateHandle.get<String>(ALL_MOVIES_ARG)

    private val mediaType = json?.let {
        Json.decodeFromString<AllMoviesMediaType>(json)
    }

    init {
        mediaType?.let {
            updateUiState {
                copy(title = mediaType.title)
            }
        }
    }

    var paginatedMovies: Flow<PagingData<Movie>> =
        when (mediaType) {
            AllMoviesMediaType.PopularMovies -> {
                getPaginatedPopularMoviesUseCase().cachedIn(viewModelScope)
            }

            is AllMoviesMediaType.SimilarMovies -> {
                getPaginatedSimilarMoviesUseCase(mediaType.movieId).cachedIn(viewModelScope)
            }

            AllMoviesMediaType.TopRated -> {
                getPaginatedTopRatedMoviesUseCase().cachedIn(viewModelScope)
            }

            null -> {
                flowOf()
            }
        }
}