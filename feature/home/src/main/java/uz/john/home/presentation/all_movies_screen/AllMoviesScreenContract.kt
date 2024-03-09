package uz.john.home.presentation.all_movies_screen

import androidx.annotation.StringRes

sealed interface AllMoviesScreenContract {
    data class UiState(
        @StringRes
        val title: Int? = null
    ) : AllMoviesScreenContract

    sealed interface SideEffect : AllMoviesScreenContract

    sealed interface UiAction : AllMoviesScreenContract
}