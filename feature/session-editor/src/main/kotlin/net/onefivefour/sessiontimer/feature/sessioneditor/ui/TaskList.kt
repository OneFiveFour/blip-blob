package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.swipedismiss.SwipeToDismissContainer
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
internal fun TaskList(
    taskGroup: UiTaskGroup,
    onUpdateTaskSortOrders: (List<Long>) -> Unit,
    onTaskTitleChanged: (Long, String) -> Unit,
    onNewTask: () -> Unit,
    onDeleteTask: (Long) -> Unit
) {
    val haptic = rememberReorderHapticFeedback()

    var taskList by remember(taskGroup.tasks) { mutableStateOf(taskGroup.tasks) }

    val lazyListState = rememberLazyListState()

    val reorderableLazyColumnState =
        rememberReorderableLazyListState(lazyListState) { from, to ->
            taskList = taskList.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
        }

    LazyColumn(
        modifier = Modifier.padding(start = 16.dp),
        state = lazyListState
    ) {
        items(
            items = taskList,
            key = { task -> task.createdAt.toEpochMilliseconds() }
        ) { task ->

            ReorderableItem(reorderableLazyColumnState, task.createdAt.toEpochMilliseconds()) {
                val interactionSource = remember { MutableInteractionSource() }

//                SwipeToDismissContainer(
//                    item = task,
//                    onDelete = { onDeleteTask(task.id) }
//                ) {

                    TaskItem(
                        modifier = Modifier
                            .longPressDraggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                    onUpdateTaskSortOrders(taskList.map { it.id })
                                },
                                interactionSource = interactionSource
                            ),
                        uiTask = task,
                        onTaskTitleChanged = { newTitle ->
                            onTaskTitleChanged(task.id, newTitle)
                        }
                    )
                }
//            }
        }

//        item {
//            AddTaskButton(
//                onNewTask = onNewTask
//            )
//        }
    }
}