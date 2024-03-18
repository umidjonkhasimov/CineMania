package uz.john.details.person_details

import uz.john.domain.model.person.details.PersonDetails

sealed interface PersonDetailsScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val personDetails: PersonDetails? = null
    ) : PersonDetailsScreenContract

    sealed interface SideEffect : PersonDetailsScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }

    sealed interface UiAction : PersonDetailsScreenContract {
        data object GetPersonDetails : UiAction
    }
}