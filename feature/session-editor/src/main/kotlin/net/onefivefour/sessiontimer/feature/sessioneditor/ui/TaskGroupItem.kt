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
import net.onefivefour.sessiontimer.core.ui.glow.drawGlowingSides
import net.onefivefour.sessiontimer.core.ui.utils.toPx
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

internal val TASK_GROUP_ITEM_HEIGHT = 64.dp

@Composable
internal fun TaskGroupItem(
    modifier: Modifier = Modifier,
    uiTaskGroup: UiTaskGroup,
    isCollapsed: Boolean,
    isDragging: Boolean,
    onCollapseChanged: (Boolean) -> Unit,
    openTaskGroupEditor: (Long) -> Unit,
    onAction: (SessionEditorAction) -> Unit
) {

    val targetHeight = when {
        isCollapsed -> TASK_GROUP_ITEM_HEIGHT
        else -> TASK_GROUP_ITEM_HEIGHT + // TaskGroupSummary
                (TASK_ITEM_HEIGHT * uiTaskGroup.tasks.size) + // TaskItems
                TASK_ITEM_HEIGHT // AddTaskButton
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

    Box(modifier = modifier.drawWithContent {
        drawGlowingSides(
            glowColor = glowColor,
            backgroundColor = backgroundColor,
            blurRadius = blurRadius
        )
    }) {

        Column(modifier = Modifier.height(animatedCollapse)) {

            TaskGroupSummary(
                uiTaskGroup = uiTaskGroup,
                onEditTaskGroup = { openTaskGroupEditor(uiTaskGroup.id) },
                onCollapseChanged = onCollapseChanged,
                isCollapsed = isCollapsed
            )

            TaskList(
                taskGroup = uiTaskGroup,
                onAction = onAction
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
