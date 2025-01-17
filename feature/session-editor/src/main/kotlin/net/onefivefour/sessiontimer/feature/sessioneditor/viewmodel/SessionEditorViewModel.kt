package net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel

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
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.NewTaskUseCase
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
    private val setTaskGroupSortOrdersUseCase: SetTaskGroupSortOrdersUseCase,
    private val setTaskSortOrdersUseCase: SetTaskSortOrdersUseCase
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

    fun newTaskGroup() {
        viewModelScope.launch {
            newTaskGroupUseCase.execute(sessionId)
        }
    }

    fun deleteTaskGroup(taskGroupId: Long) {
        viewModelScope.launch {
            deleteTaskGroupUseCase.execute(taskGroupId)
        }
    }

    fun newTask(taskGroupId: Long) {
        viewModelScope.launch {
            newTaskUseCase.execute(taskGroupId)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(taskId)
        }
    }

    fun setTaskGroupSortOrders(taskGroupIds: List<Long>) {
        viewModelScope.launch {
            setTaskGroupSortOrdersUseCase.execute(taskGroupIds)
        }
    }

    fun setTaskSortOrders(taskIds: List<Long>) {
        viewModelScope.launch {
            setTaskSortOrdersUseCase.execute(taskIds)
        }
    }

    fun setTaskTitle(taskId: Long, newTitle: String) {
        viewModelScope.launch {
            setTaskTitleUseCase.execute(
                taskId = taskId,
                title = newTitle
            )
        }
    }
}
