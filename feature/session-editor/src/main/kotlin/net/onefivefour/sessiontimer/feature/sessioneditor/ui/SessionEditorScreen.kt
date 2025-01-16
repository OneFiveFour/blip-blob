package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorViewModel

@Composable
fun SessionEditorScreen(onEditTaskGroup: (Long) -> Unit) {

    val viewModel: SessionEditorViewModel = hiltViewModel()
    val sessionEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    SessionEditor(
        uiState = sessionEditorState,
        onNewTask = viewModel::newTask,
        onEditTaskGroup = onEditTaskGroup,
        onUpdateTaskGroupSortOrders = viewModel::updateTaskGroupSortOrders,
        onUpdateTaskSortOrders = viewModel::updateTaskSortOrders
    )
}
