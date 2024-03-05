package uz.john.authentication.presentation.sign_in_screen

sealed interface SignInScreenContract {
    data class UiState(val isLoading: Boolean) : SignInScreenContract

    sealed interface UiAction : SignInScreenContract {
        data class OnSignInClick(val username: String, val password: String) : UiAction
    }

    sealed interface SideEffect : SignInScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
        data object NavigateToMainScreen : SideEffect
    }
}
