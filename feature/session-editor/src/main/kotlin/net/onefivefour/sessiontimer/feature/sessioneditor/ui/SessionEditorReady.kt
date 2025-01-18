package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.button.PrimaryButton
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession
import sh.calvin.reorderable.ReorderableColumn

@Composable
internal fun SessionEditorReady(
    uiSession: UiSession,
    onNewTask: (Long) -> Unit,
    onNewTaskGroup: () -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onUpdateTaskSortOrders: (List<Long>) -> Unit,
    onUpdateTaskGroupSortOrders: (List<Long>) -> Unit,
    onTaskTitleChanged: (Long, String) -> Unit
) {
    val haptic = rememberReorderHapticFeedback()

    var taskGroupList by remember(uiSession.taskGroups) { mutableStateOf(uiSession.taskGroups) }

    var collapsedTaskGroupsIds by remember { mutableStateOf(setOf<Long>()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReorderableColumn(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            list = taskGroupList,
            onSettle = { fromIndex, toIndex ->
                taskGroupList = taskGroupList.toMutableList().apply {
                    add(toIndex, removeAt(fromIndex))
                }
                onUpdateTaskGroupSortOrders(taskGroupList.map { it.id })
            },
            onMove = {
                haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
            },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) { _, taskGroup, _ ->
            key(taskGroup.id) {
                val interactionSource = remember { MutableInteractionSource() }

                var isDragging by remember { mutableStateOf(false) }

                TaskGroupItem(
                    modifier = Modifier
                        .longPressDraggableHandle(
                            onDragStarted = {
                                haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                isDragging = true
                            },
                            onDragStopped = {
                                haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                isDragging = false
                            },
                            interactionSource = interactionSource
                        ),
                    uiTaskGroup = taskGroup,
                    isCollapsed = collapsedTaskGroupsIds.contains(taskGroup.id),
                    isDragging = isDragging,
                    onNewTask = {
                        onNewTask(taskGroup.id)
                    },
                    onEditTaskGroup = {
                        onEditTaskGroup(taskGroup.id)
                    },
                    onUpdateTaskSortOrders = onUpdateTaskSortOrders,
                    onCollapseChanged = { isCollapsed ->
                        collapsedTaskGroupsIds = when {
                            isCollapsed -> collapsedTaskGroupsIds + taskGroup.id
                            else -> collapsedTaskGroupsIds - taskGroup.id
                        }
                    },
                    onTaskTitleChanged = onTaskTitleChanged
                )
            }
        }

        PrimaryButton(
            text = "New Task Group",
            onClick = onNewTaskGroup,
            iconRes = R.drawable.ic_add
        )
    }
}
