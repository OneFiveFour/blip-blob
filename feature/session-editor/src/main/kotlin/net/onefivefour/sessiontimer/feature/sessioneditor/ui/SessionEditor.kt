package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.runtime.Composable
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.UiState

@Composable
internal fun SessionEditor(
    uiState: UiState,
    onNewTask: (Long) -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onUpdateTaskGroupSortOrders: (List<Long>) -> Unit
) {
    when (uiState) {
        UiState.Initial -> {
            SessionEditorInitial("Initial Screen")
            return
        }
        is UiState.Error -> {
            SessionEditorError(uiState.message)
            return
        }
        is UiState.Ready -> {
            checkNotNull(uiState.uiSession)
            SessionEditorReady(
                uiSession = uiState.uiSession,
                onNewTask = onNewTask,
                onEditTaskGroup = onEditTaskGroup,
                onUpdateTaskGroupSortOrders = onUpdateTaskGroupSortOrders
            )
        }
    }
}

