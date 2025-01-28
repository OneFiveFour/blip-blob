package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.swipedismiss.SwipeToDismissContainer
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
internal fun TaskList(
    modifier: Modifier,
    uiTaskGroup: UiTaskGroup,
    onAction: (SessionEditorAction) -> Unit,
) {
    val haptic = rememberReorderHapticFeedback()

    var taskList by remember(uiTaskGroup.tasks) { mutableStateOf(uiTaskGroup.tasks) }

    val lazyListState = rememberLazyListState()

    val reorderableLazyColumnState =
        rememberReorderableLazyListState(lazyListState) { from, to ->
            taskList = taskList.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
        }

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        items(
            items = taskList,
            key = { task -> task.createdAt.toEpochMilliseconds() }
        ) { task ->

            ReorderableItem(reorderableLazyColumnState, task.createdAt.toEpochMilliseconds()) {
                val interactionSource = remember { MutableInteractionSource() }

                SwipeToDismissContainer(
                    item = task,
                    onDelete = { onAction(SessionEditorAction.DeleteTask(task.id, uiTaskGroup.id)) }
                ) {

                    TaskItem(
                        modifier = Modifier
                            .longPressDraggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                    val taskIds = taskList.map { it.id }
                                    onAction(SessionEditorAction.UpdateTaskGroupSortOrders(taskIds))
                                },
                                interactionSource = interactionSource
                            ),
                        uiTask = task,
                        onTaskTitleChanged = { newTitle ->
                            onAction(SessionEditorAction.SetTaskTitle(task.id, newTitle))
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskListPreview() {
    SessionTimerTheme {
        Surface {
            TaskList(
                modifier = Modifier,
                uiTaskGroup = fakeUiTaskGroup(),
                onAction = { _ -> }
            )
        }
    }
}