package uz.john.paginated_movies_list.all_tv_shows_screen

sealed interface AllTvShowsScreenContract {
    data class UiState(
        val title: String? = null
    ) : AllTvShowsScreenContract

    sealed interface UiAction : AllTvShowsScreenContract

    sealed interface SideEffect : AllTvShowsScreenContract
}