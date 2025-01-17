package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.runtime.Composable
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.UiState

@Composable
internal fun SessionEditor(
    uiState: UiState,
    onNewTask: (Long) -> Unit,
    onNewTaskGroup: () -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onUpdateTaskGroupSortOrders: (List<Long>) -> Unit,
    onUpdateTaskSortOrders: (List<Long>) -> Unit,
    onTaskTitleChanged: (Long, String) -> Unit
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
                onNewTaskGroup = onNewTaskGroup,
                onEditTaskGroup = onEditTaskGroup,
                onUpdateTaskGroupSortOrders = onUpdateTaskGroupSortOrders,
                onUpdateTaskSortOrders = onUpdateTaskSortOrders,
                onTaskTitleChanged = onTaskTitleChanged
            )
        }
    }
}
