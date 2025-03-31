package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorViewModel

@Composable
fun SessionEditorScreen(openTaskGroupEditor: (Long) -> Unit) {
    val viewModel: SessionEditorViewModel = hiltViewModel()

    val sessionEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onTaskGroupCreated.collect { taskGroupId ->
            openTaskGroupEditor(taskGroupId)
        }
    }

    SessionEditor(
        uiState = sessionEditorState,
        onAction = { action ->
            viewModel.onAction(action)
        },
        openTaskGroupEditor = openTaskGroupEditor
    )
}
