package uz.john.details.person_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.john.details.person_details.PersonDetailsScreenContract.SideEffect
import uz.john.details.person_details.PersonDetailsScreenContract.UiAction
import uz.john.details.person_details.PersonDetailsScreenContract.UiState
import uz.john.domain.use_cases.person.GetPersonDetailsUseCase
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import uz.john.util.ResultModel
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val personId = savedStateHandle.get<String>(PERSON_DETAILS_ARG)?.toIntOrNull()

    init {
        onAction(UiAction.GetPersonDetails)
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            updateUiState {
                copy(isLoading = true)
            }
            when (uiAction) {
                UiAction.GetPersonDetails -> {
                    getPersonDetails()
                }
            }
        }
    }

    private suspend fun getPersonDetails() = coroutineScope {
        personId?.let {
            val response = getPersonDetailsUseCase(personId)
            when (response) {
                is ResultModel.Error -> {
                    emitSideEffect(SideEffect.ShowErrorDialog(response.error.errorMessage))
                }

                is ResultModel.Exception -> {
                    response.throwable.message?.let {
                        emitSideEffect(SideEffect.ShowErrorDialog(it))
                    }
                }

                is ResultModel.Success -> {
                    updateUiState {
                        copy(
                            isLoading = false,
                            personDetails = response.data
                        )
                    }
                }
            }
        }
    }
}