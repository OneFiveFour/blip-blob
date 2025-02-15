package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState

@Composable
internal fun TaskProgressArc(
    modifier: Modifier = Modifier,
    uiTimerState: () -> UiTimerState,
    task: UiTask?,
    width: Dp,
    startAngle: Float,
    endAngle: Float
) {
    fun calculateTaskProgress(task: UiTask, uiTimerState: UiTimerState): Float {
        return when (uiTimerState) {
            is UiTimerState.Finished -> 1f
            is UiTimerState.Initial -> 0f
            is UiTimerState.Active -> {
                val isCurrentTask = task == uiTimerState.currentTask
                val taskIndex = uiTimerState.tasks.indexOf(task)
                val currentTaskIndex = uiTimerState.tasks.indexOf(uiTimerState.currentTask)
                when {
                    isCurrentTask ->
                        (uiTimerState.elapsedTaskDuration / task.taskDuration)
                            .toFloat()
                            .coerceIn(0f, 1f)
                    taskIndex < currentTaskIndex -> 1f
                    else -> 0f
                }
            }
        }
    }

    val state = uiTimerState()

    if (task == null && state !is UiTimerState.Active) return

    val t = task ?: (state as UiTimerState.Active).currentTask ?: return
    val glowScale = if (task == null) 1.03f else 1f
    val glowWidth = if (task == null) 15.dp else 10.dp
    val glowRadius = if (task == null) 15.dp else 8.dp

    val progress = calculateTaskProgress(t, state)

    CircluarProgressNeon(
        modifier = modifier.aspectRatio(1f),
        strokeColor = t.taskGroupColor,
        strokeWidth = width,
        glowWidth = glowWidth,
        glowRadius = glowRadius,
        glowScale = glowScale,
        startAngle = startAngle,
        progress = (endAngle / 360f) * progress
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskProgressArcPreview() {
    SessionTimerTheme {
        Surface {
            TaskProgressArc(
                uiTimerState = { uiTimerStateActive },
                task = task1,
                width = 11.dp,
                startAngle = 0f,
                endAngle = 360f
            )
        }
    }
}
