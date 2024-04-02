package uz.john.for_you.presentation.for_you_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.john.domain.use_cases.movies.GetFavoriteMoviesUseCase
import uz.john.domain.use_cases.movies.GetWatchLaterMoviesUseCase
import uz.john.domain.use_cases.tv_shows.GetFavoriteTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.GetWatchLaterTvShowsUseCase
import uz.john.domain.use_cases.user_data.GetUserPreferencesUseCase
import uz.john.for_you.presentation.for_you_screen.ForYouScreenContract.SideEffect
import uz.john.for_you.presentation.for_you_screen.ForYouScreenContract.UiAction
import uz.john.for_you.presentation.for_you_screen.ForYouScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class ForYouScreenViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getWatchLaterMoviesUseCase: GetWatchLaterMoviesUseCase,
    private val getFavoriteTvShowsUseCase: GetFavoriteTvShowsUseCase,
    private val getWatchLaterTvShowsUseCase: GetWatchLaterTvShowsUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            UiAction.GetSavedMovies -> {
                viewModelScope.launch {
                    getUserPreferencesUseCase().collect {
                        updateUiState { copy(isLoggedIn = it.isLoggedIn) }
                        if (it.isLoggedIn) {
                            updateUiState { copy(isLoading = true) }
                            getFavoriteMovies()
                            getWatchLaterMovies()
                            getFavoriteTvShows()
                            getWatchLaterTvShows()
                            updateUiState { copy(isLoading = false) }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getFavoriteMovies() = coroutineScope {
        val response = getFavoriteMoviesUseCase()
        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(favoriteMovies = response.data.take(10))
                }
            }
        }
    }

    private suspend fun getWatchLaterMovies() = coroutineScope {
        val response = getWatchLaterMoviesUseCase()
        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(watchLaterMovies = response.data.take(10))
                }
            }
        }
    }

    private suspend fun getFavoriteTvShows() = coroutineScope {
        val response = getFavoriteTvShowsUseCase()
        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(favoriteTvShows = response.data.take(10))
                }
            }
        }
    }

    private suspend fun getWatchLaterTvShows() = coroutineScope {
        val response = getWatchLaterTvShowsUseCase()
        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(watchLaterTvShows = response.data.take(10))
                }
            }
        }
    }
}