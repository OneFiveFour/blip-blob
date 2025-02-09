package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
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
    lazyListState: LazyListState,
    uiTaskGroup: UiTaskGroup,
    onAction: (SessionEditorAction) -> Unit,
) {
    var taskList by remember(uiTaskGroup.tasks) { mutableStateOf(uiTaskGroup.tasks) }

    val haptic = rememberReorderHapticFeedback()

    val reorderableLazyColumnState =
        rememberReorderableLazyListState(lazyListState) { from, to ->
            taskList = taskList.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
        }

    // scroll down to new items once they are added
    var previousItemCount by remember { mutableIntStateOf(taskList.size) }
    val localDensity = LocalDensity.current.density
    LaunchedEffect(taskList.size) {
        if (taskList.size > previousItemCount) {
            val lastIndex = taskList.size - 1
            val distance = lastIndex - (lazyListState.firstVisibleItemIndex)
            val offset = distance * 64 * localDensity
            lazyListState.animateScrollBy(offset, tween(1000))
        }
        previousItemCount = taskList.size
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        state = lazyListState
    ) {
        items(
            items = taskList,
            key = { task -> task.createdAt.toEpochMilliseconds() }
        ) { task ->

            ReorderableItem(
                state = reorderableLazyColumnState,
                key = task.createdAt.toEpochMilliseconds()
            ) {
                val interactionSource = remember { MutableInteractionSource() }

                SwipeToDismissContainer(
                    item = task,
                    onDelete = {
                        onAction(
                            SessionEditorAction.DeleteTask(
                                taskId = task.id,
                                taskGroupId = uiTaskGroup.id
                            )
                        )
                    }
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
                                    onAction(SessionEditorAction.UpdateTaskSortOrders(taskIds))
                                },
                                interactionSource = interactionSource
                            ),
                        uiTask = task,
                        onAction = onAction
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
                lazyListState = rememberLazyListState(),
                uiTaskGroup = fakeUiTaskGroup(),
                onAction = { _ -> }
            )
        }
    }
}