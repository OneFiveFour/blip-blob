package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

@Composable
internal fun TaskItem(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    nextTaskId: Long?,
    onAction: (SessionEditorAction) -> Unit,
) {

    println("+++ taskId: ${uiTask.id}, nextTaskId: $nextTaskId")

    val taskEditMode = LocalTaskEditMode.current

    Row(
        modifier = modifier
            .height(64.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {

        DragHandler()

        Spacer(Modifier.width(12.dp))

        val focusRequester = remember { FocusRequester() }

        if (taskEditMode.value.isEditing(uiTask.id)) {

            val textFieldState = rememberTextFieldState(
                initialText = uiTask.title,
                initialSelection = TextRange(uiTask.title.length)
            )

            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                inputTransformation = {
                    val newTitle = asCharSequence().toString()
                    onAction(SessionEditorAction.SetTaskTitle(uiTask.id, newTitle))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = when(nextTaskId) {
                        null -> ImeAction.Done
                        else -> ImeAction.Next
                    }
                ),
                onKeyboardAction = {
                    taskEditMode.value = when(nextTaskId) {
                        null -> TaskEditMode.None
                        else -> TaskEditMode.TaskTitle(nextTaskId)
                    }
                },
                state = textFieldState,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.titleSmall
                    .copy(color = MaterialTheme.colorScheme.onSurface),
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

        } else {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable { taskEditMode.value = TaskEditMode.TaskTitle(uiTask.id) },
                text = uiTask.title,
                style = MaterialTheme.typography.titleSmall
                    .copy(color = MaterialTheme.colorScheme.onSurface),
            )
        }


        Text(
            modifier = Modifier.clickable {
                taskEditMode.value = TaskEditMode.TaskDuration(uiTask.id)
            },
            text = uiTask.duration.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreview() {
    SessionTimerTheme {
        Surface {
            TaskItem(
                uiTask = uiTask3,
                nextTaskId = 2L,
                onAction = { }
            )
        }
    }
}
