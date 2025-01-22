package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.UpdateTaskGroupUseCase
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.TaskGroupEditorRoute
import kotlin.time.Duration

@HiltViewModel
internal class TaskGroupEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTaskGroupUseCase: GetTaskGroupUseCase,
    private val updateTaskGroupUseCase: UpdateTaskGroupUseCase
) : ViewModel() {

    private val taskGroupId = savedStateHandle.toRoute<TaskGroupEditorRoute>().taskGroupId

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTaskGroupUseCase.execute(taskGroupId).collectLatest { taskGroup ->
                _uiState.update {
                    UiState.Ready(taskGroup.toUiTaskGroup())
                }
            }
        }
    }

    fun onAction(action: TaskGroupEditorAction) {
        when (action) {
            is TaskGroupEditorAction.SetTitle -> {
                setTitle(action.newTitle)
            }
            is TaskGroupEditorAction.SetColor -> {
                setColor(action.newColor)
            }
            is TaskGroupEditorAction.SetPlayMode -> {
                setPlayMode(action.newPlayMode, action.newNumberOfRandomTasks)
            }
            is TaskGroupEditorAction.SetDefaultTaskDuration -> {
                setDefaultTaskDuration(action.defaultTaskDuration)
            }
        }
    }

    private fun setTitle(newTitle: String) {
        whenReady { taskGroup ->
            updateTaskGroupUseCase.execute(
                id = taskGroup.id,
                title = newTitle,
                color = taskGroup.color.toArgb(),
                playMode = taskGroup.playMode,
                numberOfRandomTasks = taskGroup.numberOfRandomTasks,
                defaultTaskDuration = taskGroup.defaultTaskDuration,
                sortOrder = taskGroup.sortOrder
            )
        }
    }

    private fun setColor(newColor: Color) {
        whenReady { taskGroup ->
            updateTaskGroupUseCase.execute(
                id = taskGroup.id,
                title = taskGroup.title,
                color = newColor.toArgb(),
                playMode = taskGroup.playMode,
                numberOfRandomTasks = taskGroup.numberOfRandomTasks,
                defaultTaskDuration = taskGroup.defaultTaskDuration,
                sortOrder = taskGroup.sortOrder
            )
        }
    }

    private fun setPlayMode(newPlayMode: PlayMode, newNumberOfRandomTasks: Int) {
        whenReady { taskGroup ->
            updateTaskGroupUseCase.execute(
                id = taskGroup.id,
                title = taskGroup.title,
                color = taskGroup.color.toArgb(),
                playMode = newPlayMode,
                numberOfRandomTasks = newNumberOfRandomTasks,
                defaultTaskDuration = taskGroup.defaultTaskDuration,
                sortOrder = taskGroup.sortOrder
            )
        }
    }

    private fun setDefaultTaskDuration(newDefaultTaskDuration: Duration) {
        whenReady { taskGroup ->
            updateTaskGroupUseCase.execute(
                id = taskGroup.id,
                title = taskGroup.title,
                color = taskGroup.color.toArgb(),
                playMode = taskGroup.playMode,
                numberOfRandomTasks = taskGroup.numberOfRandomTasks,
                defaultTaskDuration = newDefaultTaskDuration,
                sortOrder = taskGroup.sortOrder
            )
        }
    }

    private fun whenReady(action: suspend (UiTaskGroup) -> Unit) {
        _uiState.value.let {
            if (it is UiState.Ready) {
                viewModelScope.launch {
                    action(it.taskGroup)
                }
            }
        }
    }
}
