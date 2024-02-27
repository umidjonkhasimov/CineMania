package uz.john.cinemania

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.john.data.model.UserData
import uz.john.data.repository.UserDataRepository
import uz.john.util.NetworkMonitor
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val userData = userDataRepository.userData.map {
        UiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading
    )

    fun setIsOnboarded(isOnboarded: Boolean) {
        viewModelScope.launch {
            userDataRepository.setIsOnboarded(isOnboarded)
        }
    }
}

sealed class UiState {
    data object Loading : UiState()
    data class Success(val userData: UserData) : UiState()
}