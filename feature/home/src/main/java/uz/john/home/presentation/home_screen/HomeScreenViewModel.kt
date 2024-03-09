package uz.john.home.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.john.domain.use_cases.GetNowPlayingMoviesUseCase
import uz.john.domain.use_cases.GetPopularMoviesUseCase
import uz.john.domain.use_cases.GetTopRatedMoviesUseCase
import uz.john.home.presentation.home_screen.HomeScreenContract.SideEffect
import uz.john.home.presentation.home_screen.HomeScreenContract.UiAction
import uz.john.home.presentation.home_screen.HomeScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    init {
        initializeScreen()
    }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            UiAction.InitializeScreen -> {
                initializeScreen()
            }
        }
    }

    private fun initializeScreen() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            getNowPlayingMovies()
            getPopularMovies()
            getTopRatedMovies()
            updateUiState { copy(isLoading = false) }
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
                    copy(nowPlayingMovies = nowPlayingResponse.data.take(10))
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
                    copy(popularMovies = popularMoviesResponse.data.take(10))
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
                    copy(topRated = topRatedMoviesResponse.data.take(10))
                }
            }
        }
    }
}