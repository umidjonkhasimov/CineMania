package uz.john.ui.delegations

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MVI<UiState, UiAction, SideEffect> {
    val uiState: StateFlow<UiState>
    val sideEffect: Flow<SideEffect>

    fun onAction(uiAction: UiAction)

    fun updateUiState(newUiState: UiState)

    fun updateUiState(block: UiState.() -> UiState)

    fun CoroutineScope.emitSideEffect(sideEffect: SideEffect)
}