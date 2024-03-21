package uz.john.paginated_movies_list.all_tv_shows_screen

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
import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.use_cases.tv_shows.pagination.GetPaginatedRecommendedTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.pagination.GetPaginatedSimilarTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.pagination.GetPaginatedTrendingThisWeekTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.pagination.GetPaginatedTvShowsBySearchQueryUseCase
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenContract.SideEffect
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenContract.UiAction
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import javax.inject.Inject

@HiltViewModel
class AllTvShowsViewModel @Inject constructor(
    getPaginatedRecommendedTvShowsUseCase: GetPaginatedRecommendedTvShowsUseCase,
    getPaginatedSimilarTvShowsUseCase: GetPaginatedSimilarTvShowsUseCase,
    getPaginatedTvShowsBySearchQueryUseCase: GetPaginatedTvShowsBySearchQueryUseCase,
    getPaginatedTrendingThisWeekTvShowsUseCase: GetPaginatedTrendingThisWeekTvShowsUseCase,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val json = savedStateHandle.get<String>(ALL_TV_SHOWS_ARG)

    private val allTvShowsParam = json?.let {
        Json.decodeFromString<AllTvShowsScreenParam>(it)
    }

    init {
        updateUiState {
            copy(title = allTvShowsParam?.getTitle(context))
        }
    }

    val paginatedItems: Flow<PagingData<TvShow>> = when (allTvShowsParam) {
        is AllTvShowsScreenParam.RecommendedTvShows -> {
            getPaginatedRecommendedTvShowsUseCase(allTvShowsParam.seriesId).cachedIn(viewModelScope)
        }

        is AllTvShowsScreenParam.SimilarTvShows -> {
            getPaginatedSimilarTvShowsUseCase(allTvShowsParam.seriesId).cachedIn(viewModelScope)
        }

        is AllTvShowsScreenParam.TvShowsBySearchResult -> {
            getPaginatedTvShowsBySearchQueryUseCase(allTvShowsParam.query).cachedIn(viewModelScope)
        }

        AllTvShowsScreenParam.TvShowsTrendingThisWeek -> {
            getPaginatedTrendingThisWeekTvShowsUseCase().cachedIn(viewModelScope)
        }

        null -> flowOf()
    }
}