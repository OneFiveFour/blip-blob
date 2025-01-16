package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import net.onefivefour.sessiontimer.core.theme.typography
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState


internal val TASK_ITEM_HEIGHT = 64.dp
internal val TASK_GROUP_ITEM_HEIGHT = 64.dp

@Composable
internal fun TaskGroupItem(
    modifier: Modifier = Modifier,
    taskGroup: UiTaskGroup,
    isCollapsed: Boolean,
    onNewTask: (Long) -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onCollapseChanged: (Boolean) -> Unit
) {

    val animatedCollapse by animateDpAsState(
        targetValue = when {
            isCollapsed -> TASK_GROUP_ITEM_HEIGHT
            else -> TASK_GROUP_ITEM_HEIGHT + (TASK_ITEM_HEIGHT * taskGroup.tasks.size)
        },
        animationSpec = tween(500, easing = EaseOutQuint),
        label = "height"
    )


    Column(
        modifier = modifier.height(animatedCollapse)
    ) {

        Row(
            modifier = Modifier
                .height(TASK_GROUP_ITEM_HEIGHT)
        ) {
            Text(
                modifier = Modifier
                    .background(taskGroup.color)
                    .clickable { onEditTaskGroup(taskGroup.id) },
                color = MaterialTheme.colorScheme.onBackground,
                text = taskGroup.title,
                style = typography.titleLarge
            )

            Button(
                modifier = Modifier.wrapContentSize(),
                onClick = { onNewTask(taskGroup.id) }
            ) {
                Text(text = "New Task")
            }

            Button(
                modifier = Modifier.wrapContentSize(),
                onClick = { onCollapseChanged(!isCollapsed) }
            ) {
                Text(text = "Col")
            }
        }


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
            state = lazyListState
        ) {
            items(
                items = taskList,
                key = { task -> task.id }
            ) { task ->

                ReorderableItem(reorderableLazyColumnState, task.id) {
                    val interactionSource = remember { MutableInteractionSource() }

                    TaskItem(
                        modifier = Modifier
                            .longPressDraggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                },
                                interactionSource = interactionSource
                            ),
                        task = task
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun TaskGroupItemPreview() {
    SessionTimerTheme {
        Surface {
            val uiTaskGroup = UiTaskGroup(
                id = 1L,
                title = "TaskGroup Title",
                color = MaterialTheme.taskGroupColors.color03,
                playMode = PlayMode.SEQUENCE,
                numberOfRandomTasks = 0,
                tasks = fakeTasks
            )
            TaskGroupItem(
                taskGroup = uiTaskGroup,
                isCollapsed = false,
                onNewTask = { },
                onEditTaskGroup = { },
                onCollapseChanged = { }
            )
        }
    }
}
