package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.components.glow.drawGlowingSides
import net.onefivefour.sessiontimer.core.ui.components.utils.toPx
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup

internal val TASK_GROUP_ITEM_HEIGHT = 64.dp

@Composable
internal fun TaskGroupItem(
    modifier: Modifier = Modifier,
    taskGroup: UiTaskGroup,
    isCollapsed: Boolean,
    isDragging: Boolean,
    onNewTask: (Long) -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onUpdateTaskSortOrders: (List<Long>) -> Unit,
    onCollapseChanged: (Boolean) -> Unit,
    onTaskTitleChanged: (Long, String) -> Unit
) {

    val targetHeight = when {
        isCollapsed -> TASK_GROUP_ITEM_HEIGHT
        else -> TASK_GROUP_ITEM_HEIGHT + (TASK_ITEM_HEIGHT * taskGroup.tasks.size)
    }

    val animatedCollapse by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(500, easing = EaseOutQuint),
        label = "animatedCollapse"
    )

    val animatedDrag by animateFloatAsState(
        targetValue = when {
            isDragging -> 1f
            else -> 0f
        },
        animationSpec = tween(500),
        label = "animatedDrag"
    )

    val glowColor = taskGroup.color
        .copy(alpha = lerp(
            start = 0.3f,
            stop = 0.9f,
            fraction = animatedDrag
        ))

    val blurRadius = lerp(
        start = 12.dp.toPx(),
        stop = 24.dp.toPx(),
        fraction = animatedDrag
    )

    val backgroundColor = MaterialTheme.colorScheme.surface

    Box(modifier = modifier.drawWithContent {
        drawGlowingSides(
            glowColor = glowColor,
            backgroundColor = backgroundColor,
            blurRadius = blurRadius
        )
    }) {

        Column(modifier = Modifier.height(animatedCollapse)) {

            TaskGroupSummary(
                taskGroup,
                onEditTaskGroup,
                onCollapseChanged,
                isCollapsed
            )

            TaskList(
                taskGroup,
                onUpdateTaskSortOrders,
                onTaskTitleChanged = onTaskTitleChanged
            )
        }

    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupItemPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupItem(
                taskGroup = fakeUiTaskGroup(),
                isCollapsed = false,
                isDragging = false,
                onNewTask = { },
                onEditTaskGroup = { },
                onUpdateTaskSortOrders = { },
                onCollapseChanged = { },
                onTaskTitleChanged = { _, _ -> }
            )
        }
    }
}
