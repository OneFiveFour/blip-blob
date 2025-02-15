package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.swipedismiss.SwipeToDismissContainer
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState
import sh.calvin.reorderable.rememberReorderableLazyListState

internal val TASK_ITEM_HEIGHT = 64.dp

@Composable
internal fun LazyItemScope.TaskItem(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    reorderableData: Pair<ReorderableLazyListState, ReorderHapticFeedback>,
    onDelete: () -> Unit,
    onUpdateSortOrder: () -> Unit
) {
    val taskEditMode = LocalTaskEditMode.current

    val haptic = reorderableData.second

    ReorderableItem(
        modifier = modifier,
        state = reorderableData.first,
        key = uiTask.createdAt.toEpochMilliseconds()
    ) {
        SwipeToDismissContainer(
            item = uiTask,
            onDelete = { onDelete() }
        ) {
            val interactionSource = remember { MutableInteractionSource() }

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
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    ),
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
                        .copy(color = MaterialTheme.colorScheme.onSurface)
                )

                TaskDuration(
                    uiTask = uiTask
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreview() {
    SessionTimerTheme {
        Surface {
            LazyColumn {
                item {
                    TaskItem(
                        uiTask = uiTask3,
                        reorderableData =
                        rememberReorderableLazyListState(rememberLazyListState()) { _, _ ->
                        } to rememberReorderHapticFeedback(),
                        onDelete = { },
                        onUpdateSortOrder = { }
                    )
                }
            }
        }
    }
}
