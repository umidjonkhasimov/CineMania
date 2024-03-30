package uz.john.details.movie_details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.john.details.movie_details_screen.MovieDetailsScreenContract.SideEffect
import uz.john.details.movie_details_screen.MovieDetailsScreenContract.UiAction
import uz.john.details.movie_details_screen.MovieDetailsScreenContract.UiState
import uz.john.domain.use_cases.movies.SetMovieFavoriteUseCase
import uz.john.domain.use_cases.movies.GetMovieDetailsUseCase
import uz.john.domain.use_cases.movies.GetRecommendedMoviesUseCase
import uz.john.domain.use_cases.movies.GetSimilarMoviesUseCase
import uz.john.domain.use_cases.movies.SetMovieWatchLaterUseCase
import uz.john.domain.use_cases.user_data.GetUserPreferencesUseCase
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase,
    private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase,
    private val setMovieWatchLaterUseCase: SetMovieWatchLaterUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val movieId = savedStateHandle.get<String>(MOVIE_ID_ARG)?.toIntOrNull()

    init {
        movieId?.let {
            viewModelScope.launch {
                getUserPreferencesUseCase().collect {
                    updateUiState {
                        copy(isLoggedIn = it.isLoggedIn)
                    }
                }
            }
            onAction(UiAction.GetMovieDetails)
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.GetMovieDetails -> {
                    updateUiState { copy(isLoading = true) }
                    movieId?.let {
                        getMovieDetails(movieId)
                        getSimilarMovies(movieId)
                        getRecommendedMovies(movieId)
                    }
                    updateUiState { copy(isLoading = false) }
                }

                is UiAction.SetMovieFavorite -> {
                    if (uiState.value.movieDetails?.isFavorite == true) {
                        setMovieFavorite(movieId = uiAction.movieId, setFavorite = false)
                    } else {
                        setMovieFavorite(movieId = uiAction.movieId, setFavorite = true)
                    }
                }

                is UiAction.SetMovieWatchLater -> {
                    if (uiState.value.movieDetails?.isWatchLater == true) {
                        setMovieWatchLater(movieId = uiAction.movieId, setWatchLater = false)
                    } else {
                        setMovieWatchLater(movieId = uiAction.movieId, setWatchLater = true)
                    }
                }
            }
        }
    }

    private suspend fun setMovieWatchLater(movieId: Int, setWatchLater: Boolean) = coroutineScope {
        val response = setMovieWatchLaterUseCase(movieId = movieId, setWatchLater = setWatchLater)
        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(
                        movieDetails = movieDetails?.copy(isWatchLater = setWatchLater)
                    )
                }
            }
        }
    }

    private suspend fun setMovieFavorite(movieId: Int, setFavorite: Boolean) = coroutineScope {
        val response = setMovieFavoriteUseCase(movieId = movieId, setFavorite = setFavorite)
        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(
                        movieDetails = movieDetails?.copy(isFavorite = setFavorite)
                    )
                }
            }
        }
    }

    private suspend fun getMovieDetails(movieId: Int) = coroutineScope {
        val response = getMovieDetailsUseCase.invoke(movieId)
        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message ?: ""))
            }

            is ResultModel.Success -> {
                updateUiState {
                    // Take only 20 items from images and videos
                    val backdrops = response.data.images.backdrops.take(20)
                    val logos = response.data.images.logos.take(20)
                    val posters = response.data.images.posters.take(20)
                    val images = response.data.images.copy(
                        backdrops = backdrops,
                        logos = logos,
                        posters = posters
                    )

                    val videos = response.data.videos.copy(
                        videoList = response.data.videos.videoList.take(20)
                    )

                    val movieDetails = response.data.copy(images = images, videos = videos)

                    copy(movieDetails = movieDetails)
                }
            }
        }
    }

    private suspend fun getRecommendedMovies(movieId: Int) {
        val response = getRecommendedMoviesUseCase.invoke(movieId = movieId, 1)
        when (response) {
            is ResultModel.Error -> {
                updateUiState {
                    copy(recommendedMovies = emptyList())
                }
            }

            is ResultModel.Exception -> {
                updateUiState {
                    copy(recommendedMovies = emptyList())
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(recommendedMovies = response.data.take(10))
                }
            }
        }
    }

    private suspend fun getSimilarMovies(movieId: Int) {
        val response = getSimilarMoviesUseCase.invoke(movieId = movieId, 1)
        when (response) {
            is ResultModel.Error -> {
                updateUiState {
                    copy(similarMovies = emptyList())
                }
            }

            is ResultModel.Exception -> {
                updateUiState {
                    copy(similarMovies = emptyList())
                }
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(similarMovies = response.data.take(10))
                }
            }
        }
    }
}