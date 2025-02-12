package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.core.ui.swipedismiss.SwipeToDismissContainer
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction
import sh.calvin.reorderable.ReorderableItem

@Composable
internal fun TaskItemEditMode(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    isLastInList: Boolean,
    onAction: (SessionEditorAction) -> Unit,
    focusRequester: FocusRequester,
) {

    val taskEditMode = LocalTaskEditMode.current

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
                .weight(1f),
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
                    else -> TaskEditMode.TaskTitle // TODO focus manager down
                }
            },
            state = textFieldState,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = MaterialTheme.typography.titleSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )

        Text(
            modifier = Modifier.clickable {
                taskEditMode.value = TaskEditMode.TaskDuration
            },
            text = uiTask.duration.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )
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
