package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.buttons.EditButton
import net.onefivefour.sessiontimer.core.ui.glow.drawGlowingSides
import net.onefivefour.sessiontimer.core.ui.utils.toPx
import net.onefivefour.sessiontimer.feature.sessioneditor.R
import net.onefivefour.sessiontimer.core.ui.R as UiR
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

internal val TASK_GROUP_HEADER_HEIGHT = 64.dp
private val TASK_GROUP_FOOTER_HEIGHT = 64.dp
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
    val tasksHeight = TASK_ITEM_HEIGHT * (visibleTasks + 1) // +1 for Add Button
    val targetHeight = when {
        isCollapsed -> TASK_GROUP_HEADER_HEIGHT // TaskGroupHeader Only
        else -> TASK_GROUP_HEADER_HEIGHT + // TaskGroupHeader
                tasksHeight + // Visible Tasks + Add Button
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
        .height(animatedCollapse)
        .drawWithContent {
            drawGlowingSides(
                glowColor = glowColor,
                backgroundColor = backgroundColor,
                blurRadius = blurRadius
            )
        }
        .padding(horizontal = 16.dp)
    ) {

        TaskGroupHeader(
            taskGroupTitle = uiTaskGroup.title,
            onCollapseChanged = onCollapseChanged,
            isCollapsed = isCollapsed
        )

        TaskList(
            modifier = Modifier.height(tasksHeight),
            uiTaskGroup = uiTaskGroup,
            onAction = onAction
        )

        TaskGroupFooter(
            uiTaskGroup = uiTaskGroup,
            onEditTaskGroup = { openTaskGroupEditor(uiTaskGroup.id) },
        )
    }
}

@Composable
internal fun TaskGroupFooter(
    modifier: Modifier = Modifier,
    uiTaskGroup: UiTaskGroup,
    onEditTaskGroup: () -> Unit) {

    val playModeIconRes = when (uiTaskGroup.playMode) {
        PlayMode.SEQUENCE -> UiR.drawable.ic_play_mode_sequence
        PlayMode.N_TASKS_SHUFFLED -> UiR.drawable.ic_play_mode_shuffle
    }

    val playModeDescriptionRes = when (uiTaskGroup.playMode) {
        PlayMode.SEQUENCE -> UiR.string.play_mode_sequence
        PlayMode.N_TASKS_SHUFFLED -> UiR.string.play_mode_shuffle
    }

    Row(modifier = modifier) {
        Icon(
            painter = painterResource(playModeIconRes),
            contentDescription = stringResource(playModeDescriptionRes),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(uiTaskGroup.color)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.weight(1f))

        EditButton(onClick = onEditTaskGroup)
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
