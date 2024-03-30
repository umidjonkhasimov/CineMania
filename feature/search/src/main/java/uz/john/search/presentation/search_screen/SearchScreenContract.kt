package uz.john.search.presentation.search_screen

import uz.john.domain.model.movie.Movie
import uz.john.domain.model.person.Person
import uz.john.domain.model.tv_show.TvShow

sealed interface SearchScreenContract {
    data class UiState(
        val isLoading: Boolean = false,
        val isSearching: Boolean = false,
        val popularPeople: List<Person>? = null,
        val trendingThisWeekMovies: List<Movie>? = null,
        val trendingThisWeekTvShows: List<TvShow>? = null,
        val moviesResult: List<Movie>? = null,
        val peopleResult: List<Person>? = null,
        val tvShowResult: List<TvShow>? = null,
    ) : SearchScreenContract

    sealed interface SideEffect : SearchScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }

    sealed interface UiAction : SearchScreenContract {
        data class SearchForItems(val query: String) : UiAction
        data object ClearSearchResults : UiAction
    }
}