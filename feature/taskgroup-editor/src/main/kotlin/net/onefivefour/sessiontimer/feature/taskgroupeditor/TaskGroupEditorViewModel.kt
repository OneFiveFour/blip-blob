package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.utils.toIntOrZero
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupColorUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupDefaultTaskDurationUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupPlayModeUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupTitleUseCase
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.TaskGroupEditorRoute
import javax.inject.Inject
import kotlin.time.Duration

@OptIn(FlowPreview::class)
@HiltViewModel
internal class TaskGroupEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTaskGroupUseCase: GetTaskGroupUseCase,
    private val setTitle: SetTaskGroupTitleUseCase,
    private val setColor: SetTaskGroupColorUseCase,
    private val setPlayMode: SetTaskGroupPlayModeUseCase,
    private val setDefaultTaskDuration: SetTaskGroupDefaultTaskDurationUseCase,
) : ViewModel() {

    private val taskGroupId = savedStateHandle.toRoute<TaskGroupEditorRoute>().taskGroupId

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    // Give the user a bit of time to enter the duration before updating the database
    private val durationInputFlow = MutableSharedFlow<Duration>(extraBufferCapacity = 1)

    init {
        viewModelScope.launch {
            getTaskGroupUseCase.execute(taskGroupId).collectLatest { taskGroup ->
                _uiState.update {
                    val uiTaskGroup = taskGroup.toUiTaskGroup()
                    UiState.Ready(uiTaskGroup)
                }
            }
        }

        viewModelScope.launch {
            durationInputFlow
                .debounce(1_000)
                .collectLatest { newDefaultTaskDuration ->
                    doWhenReady { taskGroup ->
                        setDefaultTaskDuration(
                            taskGroupId = taskGroup.id,
                            newDefaultTaskDuration = newDefaultTaskDuration,
                        )
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

            is TaskGroupEditorAction.OnDurationEntered -> {
                onDurationEntered(action.newDurationString)
            }
        }
    }

    private fun onDurationEntered(newNumberString: String) {

        val newHours = newNumberString
            .take(2)

        val newMinutes = newNumberString
            .dropLast(2)
            .takeLast(2)

        val newSeconds = newNumberString
            .takeLast(2)

        val newTotalSeconds = newHours.toIntOrZero() * 3600 +
                newMinutes.toIntOrZero() * 60 +
                newSeconds.toIntOrZero()

        durationInputFlow.tryEmit(Duration.parse("${newTotalSeconds}s"))
    }

    private fun setTitle(newTitle: String) {
        doWhenReady { taskGroup ->
            setTitle(
                taskGroupId = taskGroup.id,
                newTitle = newTitle
            )
        }
    }

    private fun setColor(newColor: Color) {
        doWhenReady { taskGroup ->
            setColor(
                taskGroupId = taskGroup.id,
                newColor = newColor.toArgb()
            )
        }
    }

    private fun setPlayMode(newPlayMode: PlayMode, newNumberOfRandomTasks: Int) {
        doWhenReady { taskGroup ->
            setPlayMode(
                taskGroupId = taskGroup.id,
                newPlayMode = newPlayMode,
                newNumberOfRandomTasks = newNumberOfRandomTasks
            )
        }
    }

    private fun doWhenReady(action: suspend (UiTaskGroup) -> Unit) {
        _uiState.value.let {
            if (it is UiState.Ready) {
                viewModelScope.launch {
                    action(it.taskGroup)
                }
            }
        }
    }

    private fun updateWhenReady(action: (UiTaskGroup) -> UiTaskGroup) {
        _uiState.update { currentState ->
            if (currentState is UiState.Ready) {
                currentState.copy(taskGroup = action(currentState.taskGroup))
            } else {
                currentState
            }
        }
    }
}