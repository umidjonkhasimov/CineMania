package uz.john.details.tv_show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.john.details.tv_show_details.TvShowDetailsScreenContract.SideEffect
import uz.john.details.tv_show_details.TvShowDetailsScreenContract.UiAction
import uz.john.details.tv_show_details.TvShowDetailsScreenContract.UiState
import uz.john.domain.use_cases.tv_shows.GetRecommendedTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.GetSimilarTvShowsUseCase
import uz.john.domain.use_cases.tv_shows.GetTvShowDetailsUseCase
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getRecommendedTvShowsUseCase: GetRecommendedTvShowsUseCase,
    private val getSimilarTvShowsUseCase: GetSimilarTvShowsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val seriesId = savedStateHandle.get<String>(TV_SHOW_ID_ARG)?.toIntOrNull()

    init {
        onAction(UiAction.InitializeScreen)
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.InitializeScreen -> {
                    updateUiState { copy(isLoading = true) }

                    getTvShowDetails()
                    getRecommendedTvShows()
                    getSimilarTvShows()

                    updateUiState { copy(isLoading = false) }
                }
            }
        }
    }

    private suspend fun getRecommendedTvShows() = coroutineScope {
        seriesId?.let {
            val response = getRecommendedTvShowsUseCase(seriesId, 1)

            when (response) {
                is ResultModel.Error -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
                }

                is ResultModel.Exception -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
                }

                is ResultModel.Success -> {
                    updateUiState {
                        copy(recommendedTvShows = response.data.take(10))
                    }
                }
            }
        }
    }

    private suspend fun getSimilarTvShows() = coroutineScope {
        seriesId?.let {
            val response = getSimilarTvShowsUseCase(seriesId, 1)
            when (response) {
                is ResultModel.Error -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
                }

                is ResultModel.Exception -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
                }

                is ResultModel.Success -> {
                    updateUiState {
                        copy(similarTvShows = response.data.take(10))
                    }
                }
            }
        }
    }

    private suspend fun getTvShowDetails() = coroutineScope {
        seriesId?.let {
            val response = getTvShowDetailsUseCase(seriesId)
            when (response) {
                is ResultModel.Error -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
                }

                is ResultModel.Exception -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
                }

                is ResultModel.Success -> {
                    updateUiState {
                        copy(tvShowDetails = response.data)
                    }
                }
            }
        }
    }
}