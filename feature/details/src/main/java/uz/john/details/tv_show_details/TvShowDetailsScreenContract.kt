package uz.john.details.tv_show_details

import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.model.tv_show.tv_show_details.TvShowDetails

sealed interface TvShowDetailsScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val isLoggedIn: Boolean = false,
        val tvShowDetails: TvShowDetails? = null,
        val recommendedTvShows: List<TvShow>? = null,
        val similarTvShows: List<TvShow>? = null
    ) : TvShowDetailsScreenContract

    sealed interface SideEffect : TvShowDetailsScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }

    sealed interface UiAction : TvShowDetailsScreenContract {
        data object InitializeScreen : UiAction
        data class SetTvShowFavorite(val tvShowId: Int) : UiAction
        data class SetTvShowWatchLater(val tvShowId: Int) : UiAction
    }
}