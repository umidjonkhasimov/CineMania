package uz.john.cinemania

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.john.domain.model.UserPreferences
import uz.john.domain.use_cases.user_data.GetUserPreferencesUseCase
import uz.john.domain.use_cases.user_data.SetUserOnboardedUseCase
import uz.john.util.NetworkMonitor
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val setUserOnboardedUseCase: SetUserOnboardedUseCase
) : ViewModel() {
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val userData = getUserPreferencesUseCase.invoke().map {
        UiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading
    )

    fun setIsOnboarded(isOnboarded: Boolean) {
        viewModelScope.launch {
            setUserOnboardedUseCase.invoke(isOnboarded)
        }
    }
}

sealed class UiState {
    data object Loading : UiState()
    data class Success(val userData: UserPreferences) : UiState()
}