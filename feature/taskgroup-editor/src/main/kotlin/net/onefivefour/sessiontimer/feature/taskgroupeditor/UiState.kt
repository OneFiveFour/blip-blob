package net.onefivefour.sessiontimer.feature.taskgroupeditor

internal sealed interface UiState {
    data object Initial : UiState
    data class Ready(val taskGroup: UiTaskGroup) : UiState
}
