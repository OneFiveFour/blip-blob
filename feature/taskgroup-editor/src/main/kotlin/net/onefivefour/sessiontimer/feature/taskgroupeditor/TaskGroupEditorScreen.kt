package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskGroupEditorScreen(onSave: () -> Unit) {

    val viewModel: TaskGroupEditorViewModel = hiltViewModel()
    val taskGroupEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskGroupEditor(
        uiState = taskGroupEditorState,
        onTitleChanged = { newTitle -> viewModel.updateTitle(newTitle) },
        onColorChanged = { newColor -> viewModel.updateColor(newColor) },
        onPlayModeChanged = { newPlayMode, numberOfRandomTasks ->
            viewModel.updatePlayMode(newPlayMode, numberOfRandomTasks)
        },
        onSave = onSave
    )
}
