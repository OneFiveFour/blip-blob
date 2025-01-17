package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup

internal val TASK_ITEM_HEIGHT = 64.dp
internal val TASK_GROUP_ITEM_HEIGHT = 64.dp

@Composable
internal fun TaskGroupItem(
    modifier: Modifier = Modifier,
    taskGroup: UiTaskGroup,
    isCollapsed: Boolean,
    onNewTask: (Long) -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onUpdateTaskSortOrders: (List<Long>) -> Unit,
    onCollapseChanged: (Boolean) -> Unit,
) {
    val animatedCollapse by animateDpAsState(
        targetValue = when {
            isCollapsed -> TASK_GROUP_ITEM_HEIGHT
            else -> TASK_GROUP_ITEM_HEIGHT + (TASK_ITEM_HEIGHT * taskGroup.tasks.size)
        },
        animationSpec = tween(500, easing = EaseOutQuint),
        label = "height"
    )

    Column(modifier = modifier.height(animatedCollapse)) {

        TaskGroupSummary(
            taskGroup,
            onEditTaskGroup,
            onCollapseChanged,
            isCollapsed
        )

        TaskList(
            taskGroup,
            onUpdateTaskSortOrders
        )
    }
}

@Preview
@Composable
private fun TaskGroupItemPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupItem(
                taskGroup = fakeUiTaskGroup(),
                isCollapsed = false,
                onNewTask = { },
                onEditTaskGroup = { },
                onUpdateTaskSortOrders = { },
                onCollapseChanged = { }
            )
        }
    }
}
