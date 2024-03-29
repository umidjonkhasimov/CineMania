package uz.john.profile.presentation.profile_screen

import uz.john.domain.model.Language
import uz.john.domain.model.UiMode
import uz.john.domain.model.UserPreferences
import uz.john.domain.model.profile.UserAccountDetails

sealed interface ProfileScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val userPreferences: UserPreferences? = null,
        val accountDetails: UserAccountDetails? = null
    ) : ProfileScreenContract

    sealed interface SideEffect : ProfileScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }

    sealed interface UiAction : ProfileScreenContract {
        data object GetAccountDetails : UiAction
        data class SetIncludeAdult(val includeAdult: Boolean) : UiAction
        data class SetLanguage(val language: Language) : UiAction
        data class SetUiMode(val uiMode: UiMode) : UiAction
        data object SignOut : UiAction
    }
}