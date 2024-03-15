package uz.john.authentication.presentation.sign_in_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.john.authentication.presentation.sign_in_screen.SignInScreenContract.SideEffect
import uz.john.authentication.presentation.sign_in_screen.SignInScreenContract.UiAction
import uz.john.authentication.presentation.sign_in_screen.SignInScreenContract.UiState
import uz.john.domain.use_cases.authentication.SignInUseCase
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val signInUserCase: SignInUseCase
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {
    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnSignInClick -> {
                signIn(uiAction.username, uiAction.password)
            }
        }
    }

    private fun signIn(username: String, password: String) {
        updateUiState {
            copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = signInUserCase(username, password)
            when (result) {
                is ResultModel.Error -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(result.error.errorMessage))
                    updateUiState {
                        copy(isLoading = false)
                    }
                }

                is ResultModel.Success -> {
                    emitSideEffect(SideEffect.NavigateToMainScreen)
                    updateUiState {
                        copy(isLoading = false)
                    }
                }

                is ResultModel.Exception -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(result.throwable.message.toString()))
                }
            }
        }
    }
}

private fun initialUiState(): UiState = UiState(false)