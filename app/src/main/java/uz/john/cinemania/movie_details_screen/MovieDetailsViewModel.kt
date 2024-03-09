package uz.john.cinemania.movie_details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.john.cinemania.movie_details_screen.MovieDetailsScreenContract.SideEffect
import uz.john.cinemania.movie_details_screen.MovieDetailsScreenContract.UiAction
import uz.john.cinemania.movie_details_screen.MovieDetailsScreenContract.UiState
import uz.john.domain.use_cases.GetMovieDetailsUseCase
import uz.john.domain.use_cases.GetSimilarMoviesUseCase
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val movieId = savedStateHandle.get<String>(MOVIE_ID_ARG)?.toIntOrNull()

    init {
        movieId?.let {
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
                    }
                    updateUiState { copy(isLoading = false) }
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
                    // Take only 20 items out of images and videos
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