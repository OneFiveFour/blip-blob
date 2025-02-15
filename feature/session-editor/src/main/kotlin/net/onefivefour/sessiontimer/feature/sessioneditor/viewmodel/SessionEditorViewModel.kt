package net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionTitleUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.NewTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskDurationUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskSortOrdersUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskTitleUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupSortOrdersUseCase
import net.onefivefour.sessiontimer.feature.sessioneditor.api.SessionEditorRoute
import net.onefivefour.sessiontimer.feature.sessioneditor.model.toUiSession

@HiltViewModel
internal class SessionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSessionUseCase: GetSessionUseCase,
    private val newTaskGroupUseCase: NewTaskGroupUseCase,
    private val newTaskUseCase: NewTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteTaskGroupUseCase: DeleteTaskGroupUseCase,
    private val setTaskTitleUseCase: SetTaskTitleUseCase,
    private val setTaskDurationUseCase: SetTaskDurationUseCase,
    private val setTaskGroupSortOrdersUseCase: SetTaskGroupSortOrdersUseCase,
    private val setTaskSortOrdersUseCase: SetTaskSortOrdersUseCase,
    private val setSessionTitleUseCase: SetSessionTitleUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionEditorRoute>().sessionId

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getSessionUseCase.execute(sessionId).collectLatest { fullSession ->
                _uiState.update {
                    when (fullSession) {
                        null -> UiState.Error("Could not find a session with id $sessionId")
                        else -> UiState.Ready(uiSession = fullSession.toUiSession())
                    }
                }
            }
        }
    }

    fun onAction(action: SessionEditorAction) {
        when (action) {
            is SessionEditorAction.CreateTaskGroup -> createTaskGroup()
            is SessionEditorAction.DeleteTaskGroup -> deleteTaskGroup(action.taskGroupId)
            is SessionEditorAction.CreateTask -> createTask(action.taskGroupId)
            is SessionEditorAction.DeleteTask -> deleteTask(action.taskId, action.taskGroupId)
            is SessionEditorAction.UpdateTaskGroupSortOrders -> setTaskGroupSortOrders(
                action.taskGroupIds
            )
            is SessionEditorAction.UpdateTaskSortOrders -> setTaskSortOrders(action.taskIds)
            is SessionEditorAction.SetTaskTitle -> setTaskTitle(action.taskId, action.newTitle)
            is SessionEditorAction.SetSessionTitle -> setSessionTitle(action.newTitle)
            is SessionEditorAction.SetTaskDuration -> setTaskDuration(
                action.taskId,
                action.newDuration
            )
        }
    }

    private fun setTaskDuration(taskId: Long, newDuration: Duration) {
        viewModelScope.launch {
            setTaskDurationUseCase.execute(taskId, newDuration)
        }
    }

    private fun createTaskGroup() {
        viewModelScope.launch {
            newTaskGroupUseCase.execute(sessionId)
        }
    }

    private fun deleteTaskGroup(taskGroupId: Long) {
        viewModelScope.launch {
            deleteTaskGroupUseCase.execute(taskGroupId)
        }
    }

    private fun createTask(taskGroupId: Long) {
        viewModelScope.launch {
            newTaskUseCase.execute(taskGroupId)
        }
    }

    private fun deleteTask(taskId: Long, taskGroupId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(taskId, taskGroupId)
        }
    }

    private fun setTaskGroupSortOrders(taskGroupIds: List<Long>) {
        viewModelScope.launch {
            setTaskGroupSortOrdersUseCase.execute(taskGroupIds)
        }
    }

    private fun setTaskSortOrders(taskIds: List<Long>) {
        viewModelScope.launch {
            setTaskSortOrdersUseCase.execute(taskIds)
        }
    }

    private fun setTaskTitle(taskId: Long, newTitle: String) {
        viewModelScope.launch {
            setTaskTitleUseCase.execute(
                taskId = taskId,
                title = newTitle
            )
        }
    }

    private fun setSessionTitle(newTitle: String) {
        viewModelScope.launch {
            setSessionTitleUseCase.execute(
                sessionId = sessionId,
                title = newTitle
            )
        }
    }
}
