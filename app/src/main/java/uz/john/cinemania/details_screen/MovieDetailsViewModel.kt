package uz.john.cinemania.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.john.cinemania.details_screen.MovieDetailsScreenContract.SideEffect
import uz.john.cinemania.details_screen.MovieDetailsScreenContract.UiAction
import uz.john.cinemania.details_screen.MovieDetailsScreenContract.UiState
import uz.john.domain.use_cases.GetMovieDetailsUseCase
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import uz.john.util.logging
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.GetMovieDetails -> {
                updateUiState { copy(isLoading = true) }
                viewModelScope.launch {
                    getMovieDetails(uiAction.movieId)
                    updateUiState { copy(isLoading = false) }
                }
            }
        }
    }

    private suspend fun getMovieDetails(movieId: Int) {
        val response = getMovieDetailsUseCase.invoke(movieId)
        when (response) {
            is ResultModel.Error -> {
                logging(response.error)
            }

            is ResultModel.Exception -> {
                logging(response.throwable)
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(movieDetails = response.data)
                }
            }
        }
    }
}