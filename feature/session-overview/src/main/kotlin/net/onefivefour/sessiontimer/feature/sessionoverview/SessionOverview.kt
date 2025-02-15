package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.runtime.Composable

@Composable
internal fun SessionOverview(
    uiState: UiState,
    onEditSession: (Long) -> Unit,
    onStartSession: (Long) -> Unit,
    onAction: (SessionOverviewAction) -> Unit
) {
    when (uiState) {
        UiState.Initial -> {
            SessionOverviewInitial()
        }
        is UiState.Ready -> {
            SessionOverviewReady(
                uiState,
                onEditSession,
                onStartSession,
                onAction
            )
        }
    }
}
