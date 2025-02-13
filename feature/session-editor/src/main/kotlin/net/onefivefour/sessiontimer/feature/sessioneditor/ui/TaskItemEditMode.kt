package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

@Composable
internal fun TaskItemEditMode(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    isLastInList: Boolean,
    onAction: (SessionEditorAction) -> Unit,
    focusRequester: FocusRequester,
) {

    val taskEditMode = LocalTaskEditMode.current

    val focusManager = LocalFocusManager.current

    val textFieldState = rememberTextFieldState(
        initialText = uiTask.title,
        initialSelection = TextRange(uiTask.title.length)
    )

    Row(
        modifier = modifier
            .height(TASK_ITEM_HEIGHT)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(Modifier.width(12.dp))

        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            inputTransformation = {
                val newTitle = asCharSequence().toString()
                onAction(SessionEditorAction.SetTaskTitle(uiTask.id, newTitle))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = when {
                    isLastInList -> ImeAction.Done
                    else -> ImeAction.Next
                }
            ),
            onKeyboardAction = {
                taskEditMode.value = when {
                    isLastInList -> TaskEditMode.None
                    taskEditMode.value is TaskEditMode.TaskTitle -> {
                        focusManager.moveFocus(FocusDirection.Down)
                        return@BasicTextField
                    }
                    else -> TaskEditMode.TaskTitle(uiTask.id)
                }
            },
            state = textFieldState,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = MaterialTheme.typography.titleSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )

        TaskDuration(
            uiTask = uiTask,
            onDurationChanged = { newDuration ->
                onAction(SessionEditorAction.SetTaskDuration(uiTask.id, newDuration))
            }
            )
    }

    LaunchedEffect(taskEditMode.value) {
        val currentTaskEditMode = taskEditMode.value

        if (currentTaskEditMode !is TaskEditMode.TaskTitle) {
            return@LaunchedEffect
        }

        val isThisTaskItem = currentTaskEditMode.initialTaskId == uiTask.id
        if (isThisTaskItem) {
            focusRequester.requestFocus()
        }

    }
}

//@Preview
//@Preview(uiMode = UI_MODE_NIGHT_YES)
//@Composable
//@OptIn(ExperimentalFoundationApi::class)
//private fun TaskItemPreview() {
//    SessionTimerTheme {
//        Surface {
//            TaskItem(
//                uiTask = uiTask3,
//                nextTaskId = 2L,
//                onAction = { },
//                focusRequester = focusRequester,
////                bringIntoViewRequester = BringIntoViewRequester(),
////                focusRequester = FocusRequester(),
//            )
//        }
//    }
//}
