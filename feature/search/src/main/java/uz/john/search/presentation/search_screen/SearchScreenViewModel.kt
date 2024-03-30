package uz.john.search.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.john.domain.use_cases.movies.GetTrendingThisWeekMoviesUseCase
import uz.john.domain.use_cases.person.GetPopularPeopleUseCase
import uz.john.domain.use_cases.search.SearchMoviesUseCase
import uz.john.domain.use_cases.search.SearchPeopleUseCase
import uz.john.domain.use_cases.search.SearchTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.GetTrendingThisWeekTvShowsUseCase
import uz.john.search.presentation.search_screen.SearchScreenContract.SideEffect
import uz.john.search.presentation.search_screen.SearchScreenContract.UiAction
import uz.john.search.presentation.search_screen.SearchScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
    private val getTrendingThisWeekMoviesUseCase: GetTrendingThisWeekMoviesUseCase,
    private val getTrendingThisWeekTvShowsUseCase: GetTrendingThisWeekTvShowsUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase,
    private val searchTvShowsUseCase: SearchTvShowsUseCase,
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private var searchJob: Job = Job()

    init {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            getPopularPeople()
            getTrendingThisWeekMovies()
            getTrendingThisWeekTvShows()
            updateUiState { copy(isLoading = false) }
        }
    }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.SearchForItems -> {
                searchJob = viewModelScope.launch {
                    searchJob.cancel()
                    delay(500)

                    updateUiState { copy(isSearching = true) }

                    searchForMovies(uiAction.query)
                    searchForPeople(uiAction.query)
                    searchForTvShows(uiAction.query)

                    updateUiState { copy(isSearching = false) }
                }
            }

            UiAction.ClearSearchResults -> {
                updateUiState {
                    copy(
                        moviesResult = null,
                        peopleResult = null,
                        tvShowResult = null
                    )
                }
            }
        }
    }

    private suspend fun getPopularPeople() = coroutineScope {
        val popularPeopleResponse = getPopularPeopleUseCase.invoke(1)
        when (popularPeopleResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(popularPeopleResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                popularPeopleResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                val list = popularPeopleResponse.data.take(10)
                updateUiState { copy(popularPeople = list) }
            }
        }
    }

    private suspend fun getTrendingThisWeekMovies() = coroutineScope {
        val trendingThisWeekResponse = getTrendingThisWeekMoviesUseCase.invoke(1)
        when (trendingThisWeekResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(trendingThisWeekResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                trendingThisWeekResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                val list = trendingThisWeekResponse.data.take(10)
                updateUiState { copy(trendingThisWeekMovies = list) }
            }
        }
    }

    private suspend fun getTrendingThisWeekTvShows() = coroutineScope {
        val trendingThisWeekResponse = getTrendingThisWeekTvShowsUseCase.invoke(1)
        when (trendingThisWeekResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(trendingThisWeekResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                trendingThisWeekResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                val list = trendingThisWeekResponse.data.take(10)
                updateUiState { copy(trendingThisWeekTvShows = list) }
            }
        }
    }

    private suspend fun searchForMovies(query: String) = coroutineScope {
        val movieResult = searchMoviesUseCase.invoke(query = query, page = 1)

        when (movieResult) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(movieResult.error.errorMessage))
            }

            is ResultModel.Exception -> {
                movieResult.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(moviesResult = movieResult.data)
                }
            }
        }
    }

    private suspend fun searchForPeople(query: String) = coroutineScope {
        val peopleResult = searchPeopleUseCase.invoke(query = query, page = 1)

        when (peopleResult) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(peopleResult.error.errorMessage))
            }

            is ResultModel.Exception -> {
                peopleResult.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(peopleResult = peopleResult.data)
                }
            }
        }
    }

    private suspend fun searchForTvShows(query: String) = coroutineScope {
        val tvShowsResult = searchTvShowsUseCase.invoke(query = query, page = 1)

        when (tvShowsResult) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(tvShowsResult.error.errorMessage))
            }

            is ResultModel.Exception -> {
                tvShowsResult.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(tvShowResult = tvShowsResult.data)
                }
            }
        }
    }
}