package net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel

import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession

internal sealed interface UiState {
    data object Initial : UiState
    data class Ready(val uiSession: UiSession) : UiState
    data class Error(val message: String) : UiState
}
