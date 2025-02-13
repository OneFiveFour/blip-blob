package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.swipedismiss.SwipeToDismissContainer
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState

internal val TASK_ITEM_HEIGHT = 64.dp

@Composable
internal fun LazyItemScope.TaskItem(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    reorderableData: Pair<ReorderableLazyListState, ReorderHapticFeedback>,
    onDelete: () -> Unit,
    onUpdateSortOrder: () -> Unit,
) {

    val taskEditMode = LocalTaskEditMode.current

    val haptic = reorderableData.second

    ReorderableItem(
        modifier = modifier,
        state = reorderableData.first,
        key = uiTask.createdAt.toEpochMilliseconds()
    ) {

        val interactionSource = remember { MutableInteractionSource() }

        SwipeToDismissContainer(
            item = uiTask,
            onDelete = { onDelete() }
        ) {

            Row(
                modifier = Modifier
                    .longPressDraggableHandle(
                        onDragStarted = {
                            haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                        },
                        onDragStopped = {
                            haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                            onUpdateSortOrder()
                        },
                        interactionSource = interactionSource
                    )
                    .height(TASK_ITEM_HEIGHT)
                    .background(MaterialTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically
            ) {

                DragHandler()

                Spacer(Modifier.width(12.dp))

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { taskEditMode.value = TaskEditMode.TaskTitle(uiTask.id) },
                    text = uiTask.title,
                    style = MaterialTheme.typography.titleSmall
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
