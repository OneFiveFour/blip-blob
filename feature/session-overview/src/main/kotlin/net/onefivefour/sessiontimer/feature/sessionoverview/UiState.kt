package net.onefivefour.sessiontimer.feature.sessionoverview

internal sealed interface UiState {
    data object Initial : UiState
    data class Ready(val sessions: List<UiSession>) : UiState
}
