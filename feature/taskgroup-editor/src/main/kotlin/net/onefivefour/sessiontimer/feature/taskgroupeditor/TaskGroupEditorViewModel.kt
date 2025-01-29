package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.selectAll
import androidx.compose.runtime.snapshotFlow
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

    private val titleTextFieldState = TextFieldState()

    init {
        viewModelScope.launch {
            getTaskGroupUseCase.execute(taskGroupId).collectLatest { taskGroup ->
                _uiState.update {
                    val uiTaskGroup = taskGroup.toUiTaskGroup()
                    titleTextFieldState.edit {
                        this.delete(0, this.length)
                        this.insert(0, uiTaskGroup.title.text.toString())
                    }
                    UiState.Ready(uiTaskGroup)
                }
            }
        }

        viewModelScope.launch {
            durationInputFlow
                .debounce(1_000)
        }
    }

    fun updateUiTaskDuration() {
        viewModelScope.launch {
            snapshotFlow { titleTextFieldState }.collectLatest { newState ->
                updateWhenReady { taskGroup ->

                    val newText = newState.text
                    val numberEntered = newText.last()
                    val isNumber = numberEntered.isDigit()
                    val isBackspace = numberEntered == '\b'

                    val newNumberString = when {
                        isBackspace -> newText.dropLast(1)
                        isNumber -> "0$newText$numberEntered"
                        else -> return@updateWhenReady taskGroup
                    }

                    val newSeconds = newNumberString
                        .takeLast(2)
                        .padStart(2, '0')
                        .toString()

                    val newMinutes = newNumberString
                        .dropLast(2)
                        .takeLast(2)
                        .padStart(2, '0')
                        .toString()

                    val newHours = newNumberString
                        .dropLast(4)
                        .takeLast(3)
                        .padStart(3, '0')
                        .toString()

                    // immediately update UI
                    taskGroup.copy(
                        defaultTaskDuration = UiTaskDuration(
                            hours = newHours,
                            minutes = newMinutes,
                            seconds = newSeconds
                        )
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

            is TaskGroupEditorAction.OnDurationNumberEntered -> {
//                setDefaultTaskDuration(
//                    action.currentString,
//                    action.numberEntered
//                )
            }
        }
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
//
//    private fun setDefaultTaskDuration(
//        currentString: String,
//        numberEntered: Char,
//    ) {
//        updateWhenReady { taskGroup ->
//
//            val isNumber = numberEntered.isDigit()
//            val isBackspace = numberEntered == '\b'
//
//            val newNumberString = when {
//                isBackspace -> currentString.dropLast(1)
//                isNumber -> "0$currentString$numberEntered"
//                else -> return@updateWhenReady taskGroup
//            }
//
//            val newSeconds = newNumberString
//                .takeLast(2)
//                .padStart(2, '0')
//
//            val newMinutes = newNumberString
//                .dropLast(2)
//                .takeLast(2)
//                .padStart(2, '0')
//
//            val newHours = newNumberString
//                .dropLast(4)
//                .takeLast(3)
//                .padStart(3, '0')
//
//            val newTotalSeconds = newHours.toIntOrZero() * 3600 +
//                    newMinutes.toIntOrZero() * 60 +
//                    newSeconds.toIntOrZero()
//
//            // trigger the debounced database update
//            durationInputFlow.tryEmit(Duration.parse("${newTotalSeconds}s"))
//
//            // immediately update UI
//            taskGroup.copy(
//                defaultTaskDuration = UiTaskDuration(
//                    hours = newHours,
//                    minutes = newMinutes,
//                    seconds = newSeconds
//                )
//            )
//
//        }
//    }

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