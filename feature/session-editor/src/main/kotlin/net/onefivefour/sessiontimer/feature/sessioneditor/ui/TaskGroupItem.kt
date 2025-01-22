package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import net.onefivefour.sessiontimer.core.ui.glow.drawGlowingSides
import net.onefivefour.sessiontimer.core.ui.utils.toPx
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

internal val TASK_GROUP_HEADER_HEIGHT = 48.dp
internal val TASK_ITEM_HEIGHT = 48.dp
private val TASK_GROUP_FOOTER_HEIGHT = 70.dp
private const val MAX_VISIBLE_TASKS = 3

@Composable
internal fun TaskGroupItem(
    modifier: Modifier = Modifier,
    uiTaskGroup: UiTaskGroup,
    isCollapsed: Boolean,
    isDragging: Boolean,
    onCollapseChanged: (Boolean) -> Unit,
    openTaskGroupEditor: (Long) -> Unit,
    onAction: (SessionEditorAction) -> Unit,
) {

    val visibleTasks = minOf(uiTaskGroup.tasks.size, MAX_VISIBLE_TASKS)
    val tasksHeight = TASK_ITEM_HEIGHT * visibleTasks
    val targetHeight = when {
        isCollapsed -> TASK_GROUP_HEADER_HEIGHT // TaskGroupHeader Only
        else -> TASK_GROUP_HEADER_HEIGHT + // TaskGroupHeader
                tasksHeight + // Visible Tasks
                TASK_ITEM_HEIGHT + // CreateTaskButton
                TASK_GROUP_FOOTER_HEIGHT // TaskGroupFooter
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

    val glowColor = uiTaskGroup.color
        .copy(
            alpha = lerp(
                start = 0.3f,
                stop = 0.9f,
                fraction = animatedDrag
            )
        )

    val blurRadius = lerp(
        start = 12.dp.toPx(),
        stop = 24.dp.toPx(),
        fraction = animatedDrag
    )

    val backgroundColor = MaterialTheme.colorScheme.surface

    Column(modifier = modifier
        .drawWithContent {
            drawGlowingSides(
                glowColor = glowColor,
                backgroundColor = backgroundColor,
                blurRadius = blurRadius
            )
        }
        .padding(10.dp)
        .height(animatedCollapse)
    ) {

        TaskGroupHeader(
            modifier = Modifier.height(TASK_GROUP_HEADER_HEIGHT),
            taskGroupTitle = uiTaskGroup.title,
            onCollapseChanged = onCollapseChanged,
            isCollapsed = isCollapsed
        )

        if (!isCollapsed) {
            TaskList(
                modifier = Modifier.height(tasksHeight),
                uiTaskGroup = uiTaskGroup,
                onAction = onAction
            )

            CreateTaskButton(
                modifier = Modifier.height(TASK_ITEM_HEIGHT),
                defaultTaskDuration = uiTaskGroup.defaultTaskDuration,
                onCreateTask = { onAction(SessionEditorAction.CreateTask(uiTaskGroup.id)) }
            )

            TaskGroupFooter(
                modifier = Modifier.height(TASK_GROUP_FOOTER_HEIGHT),
                uiTaskGroup = uiTaskGroup,
                onEditTaskGroup = { openTaskGroupEditor(uiTaskGroup.id) },
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
                uiTaskGroup = fakeUiTaskGroup(),
                isCollapsed = false,
                isDragging = false,
                onCollapseChanged = { },
                openTaskGroupEditor = { },
                onAction = { _ -> }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupItemCollapsedPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupItem(
                uiTaskGroup = fakeUiTaskGroup(),
                isCollapsed = true,
                isDragging = false,
                onCollapseChanged = { },
                openTaskGroupEditor = { },
                onAction = { _ -> }
            )
        }
    }
}
