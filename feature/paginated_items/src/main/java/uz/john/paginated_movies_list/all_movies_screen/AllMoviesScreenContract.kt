package uz.john.paginated_movies_list.all_movies_screen

sealed interface AllMoviesScreenContract {
    data class UiState(
        val title: String? = null
    ) : AllMoviesScreenContract

    sealed interface SideEffect : AllMoviesScreenContract

    sealed interface UiAction : AllMoviesScreenContract
}