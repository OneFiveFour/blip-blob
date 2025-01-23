package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun TaskGroupEditor(
    uiState: UiState,
    onAction: (TaskGroupEditorAction) -> Unit,
    goBack: () -> Unit,
) {
    when (uiState) {
        UiState.Initial -> {
            Text(text = "TaskGroup Editor")
            return
        }

        is UiState.Ready -> {
            checkNotNull(uiState.taskGroup)
            TaskGroupEditorReady(
                taskGroup = uiState.taskGroup,
                onAction = onAction,
                goBack = goBack
            )
        }
    }
}

