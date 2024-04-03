package uz.john.home.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.john.domain.use_cases.movies.GetAllMovieGenresUseCase
import uz.john.domain.use_cases.movies.GetNowPlayingMoviesUseCase
import uz.john.domain.use_cases.movies.GetPopularMoviesUseCase
import uz.john.domain.use_cases.movies.GetTopRatedMoviesUseCase
import uz.john.domain.use_cases.movies.GetTrendingTodayMoviesUseCase
import uz.john.domain.use_cases.tv_shows.GetAllTvShowGenresUseCase
import uz.john.domain.use_cases.tv_shows.GetNowPlayingTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.GetPopularTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.GetTopRatedTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.GetTrendingTodayTvShowsUseCase
import uz.john.home.presentation.home_screen.HomeScreenContract.SideEffect
import uz.john.home.presentation.home_screen.HomeScreenContract.UiAction
import uz.john.home.presentation.home_screen.HomeScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    // Movies
    private val getTrendingTodayMoviesUseCase: GetTrendingTodayMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getAllMovieGenresUseCase: GetAllMovieGenresUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,

    // TvShows
    private val getTrendingTodayTvShowsUseCase: GetTrendingTodayTvShowsUseCase,
    private val getNowPlayingTvShowsUseCase: GetNowPlayingTvShowsUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getAllTvShowGenresUseCase: GetAllTvShowGenresUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    init {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }

            initializeMoviesScreen()
            initializeTvShowsScreen()

            updateUiState { copy(isLoading = false) }
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.InitializeMoviesScreen -> {
                    initializeMoviesScreen()
                }

                UiAction.InitializeTvShowsScreen -> {
                    initializeTvShowsScreen()
                }
            }
        }
    }

    private suspend fun initializeMoviesScreen() {
        getTrendingMovies()
        getNowPlayingMovies()
        getPopularMovies()
        getAllMoviesGenres()
        getTopRatedMovies()
    }

    private suspend fun initializeTvShowsScreen() {
        getTrendingTodayTvShows()
        getNowPlayingTvShows()
        getPopularTvShows()
        getAllTvShowGenres()
        getTopRatedTvShows()
    }

    private suspend fun getAllMoviesGenres() = coroutineScope {
        val allGenresresponse = getAllMovieGenresUseCase.invoke()

        when (allGenresresponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(allGenresresponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                allGenresresponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(movieGenres = allGenresresponse.data)
                }
            }
        }
    }

    private suspend fun getTrendingMovies() = coroutineScope {
        val trendingResponse =
            getTrendingTodayMoviesUseCase.invoke(1)

        when (trendingResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(trendingResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                trendingResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(trendingTodayMovies = trendingResponse.data.take(10).shuffled())
                }
            }
        }
    }

    private suspend fun getNowPlayingMovies() = coroutineScope {
        val nowPlayingResponse =
            getNowPlayingMoviesUseCase.invoke(1)

        when (nowPlayingResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(nowPlayingResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                nowPlayingResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(nowPlayingMovies = nowPlayingResponse.data.take(10).shuffled())
                }
            }
        }
    }

    private suspend fun getPopularMovies() = coroutineScope {
        val popularMoviesResponse =
            getPopularMoviesUseCase.invoke(1)

        when (popularMoviesResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(popularMoviesResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                popularMoviesResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(popularMovies = popularMoviesResponse.data.take(10).shuffled())
                }
            }
        }
    }

    private suspend fun getTopRatedMovies() = coroutineScope {
        val topRatedMoviesResponse =
            getTopRatedMoviesUseCase.invoke(1)

        when (topRatedMoviesResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(topRatedMoviesResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                topRatedMoviesResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(topRatedMovies = topRatedMoviesResponse.data.take(10).shuffled())
                }
            }
        }
    }

    private suspend fun getTrendingTodayTvShows() = coroutineScope {
        val response = getTrendingTodayTvShowsUseCase.invoke(1)

        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                response.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(trendingTodayTvShows = response.data.take(10).shuffled())
                }
            }
        }
    }

    private suspend fun getNowPlayingTvShows() = coroutineScope {
        val response = getNowPlayingTvShowsUseCase.invoke(1)

        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                response.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(nowPlayingTvShows = response.data.take(10).shuffled())
                }
            }
        }
    }

    private suspend fun getPopularTvShows() = coroutineScope {
        val response = getPopularTvShowsUseCase.invoke(1)

        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                response.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(popularTvShows = response.data.take(10).shuffled())
                }
            }
        }
    }

    private suspend fun getAllTvShowGenres() = coroutineScope {
        val allGenresresponse = getAllTvShowGenresUseCase.invoke()

        when (allGenresresponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(allGenresresponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                allGenresresponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(tvShowGenres = allGenresresponse.data)
                }
            }
        }
    }

    private suspend fun getTopRatedTvShows() = coroutineScope {
        val topRatedTvShowsResponse = getTopRatedTvShowsUseCase.invoke(1)

        when (topRatedTvShowsResponse) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(topRatedTvShowsResponse.error.errorMessage))
            }

            is ResultModel.Exception -> {
                topRatedTvShowsResponse.throwable.message?.let {
                    emitSideEffect(SideEffect.ShowErrorDialog(it))
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(topRatedTvShows = topRatedTvShowsResponse.data.take(10).shuffled())
                }
            }
        }
    }
}