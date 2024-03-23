package uz.john.paginated_movies_list.all_people_screen

sealed interface AllPeopleScreenContract {
    data class UiState(val title: String = "") : AllPeopleScreenContract

    sealed interface UiAction : AllPeopleScreenContract

    sealed interface SideEffect : AllPeopleScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }
}