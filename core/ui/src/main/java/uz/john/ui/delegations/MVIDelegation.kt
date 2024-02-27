package uz.john.ui.delegations

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MVIDelegate<UiState, UiAction, SideEffect> internal constructor(
    initialState: UiState
) : MVI<UiState, UiAction, SideEffect> {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    override val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    override fun onAction(uiAction: UiAction) {}

    override fun updateUiState(newUiState: UiState) {
        _uiState.update { newUiState }
    }

    override fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    override fun CoroutineScope.emitSideEffect(sideEffect: SideEffect) {
        launch {
            _sideEffect.send(sideEffect)
        }
    }
}

fun <UiState, UiAction, SideEffect> mvi(initialState: UiState)
        : MVI<UiState, UiAction, SideEffect> =
    MVIDelegate(initialState)