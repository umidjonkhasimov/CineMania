package uz.john.profile.presentation.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import uz.john.domain.use_cases.user_data.GetUserAccountDetailsUseCase
import uz.john.domain.use_cases.user_data.GetUserPreferencesUseCase
import uz.john.domain.use_cases.user_data.SetIncludeAdultContentUseCase
import uz.john.domain.use_cases.user_data.SetUiModeUseCase
import uz.john.domain.use_cases.user_data.SetUserLanguageUseCase
import uz.john.domain.use_cases.user_data.SetUserLoggedInUseCase
import uz.john.profile.presentation.profile_screen.ProfileScreenContract.SideEffect
import uz.john.profile.presentation.profile_screen.ProfileScreenContract.UiAction
import uz.john.profile.presentation.profile_screen.ProfileScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getUserAccountDetailsUseCase: GetUserAccountDetailsUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val setUserLanguageUseCase: SetUserLanguageUseCase,
    private val setIncludeAdultContentUseCase: SetIncludeAdultContentUseCase,
    private val setUiModeUseCase: SetUiModeUseCase,
    private val setUserLoggedInUseCase: SetUserLoggedInUseCase
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    init {
        onAction(UiAction.GetAccountDetails)
        viewModelScope.launch {
            getUserPreferencesUseCase().collect {
                updateUiState {
                    copy(userPreferences = it)
                }
            }
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.GetAccountDetails -> {
                    updateUiState { copy(isLoading = true) }
                    getUserAccountDetails()
                    updateUiState { copy(isLoading = false) }
                }

                is UiAction.SetIncludeAdult -> {
                    setIncludeAdultContentUseCase(uiAction.includeAdult)
                }

                is UiAction.SetLanguage -> {
                    setUserLanguageUseCase(uiAction.language)
                }

                is UiAction.SetUiMode -> {
                    setUiModeUseCase(uiAction.uiMode)
                }

                UiAction.SignOut -> {
                    setUserLoggedInUseCase(false)
                }
            }
        }
    }

    private suspend fun getUserAccountDetails() = coroutineScope {
        val userPrefs = getUserPreferencesUseCase().firstOrNull()
        val response = getUserAccountDetailsUseCase.invoke(
            sessionId = userPrefs?.sessionId ?: ""
        )

        when (response) {
            is ResultModel.Error -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
            }

            is ResultModel.Exception -> {
                emitSideEffect(SideEffect.ShowErrorDialog(response.throwable.message.toString()))
            }

            is ResultModel.Success -> {
                updateUiState {
                    copy(accountDetails = response.data)
                }
            }
        }
    }
}
