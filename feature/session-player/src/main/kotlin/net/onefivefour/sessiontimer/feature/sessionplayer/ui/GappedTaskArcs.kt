package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier.arc

@Composable
internal fun GappedTaskArcs(uiState: UiState.Ready, uiTimerState: () -> UiTimerState) {
    Box(
        modifier = Modifier
            .padding(44.dp)
            .aspectRatio(1f)
    ) {
        val gapAngle = 5f
        var startAngle = gapAngle / 2

        uiState.tasks.forEach { task ->

            val durationRatio = (task.taskDuration / uiState.totalDuration).toFloat()
            val sweepAngle = 360f * durationRatio - gapAngle

            // task indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .arc(
                        color = task.taskGroupColor,
                        width = 1.dp,
                        progress = sweepAngle / 360f,
                        startAngle = startAngle
                    )
            )

            // task progress
            TaskProgressArc(
                uiTimerState = uiTimerState,
                task = task,
                width = 5.dp,
                startAngle = startAngle,
                endAngle = sweepAngle
            )

            startAngle += sweepAngle + gapAngle
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GappedTaskArcsFinishedPreview() {
    SessionTimerTheme {
        Surface {
            val timerState = UiTimerState.Finished

            val uiState = UiState.Ready(
                sessionTitle = "Session Title",
                totalDuration = 60.seconds,
                tasks = fakeTasks
            )

            GappedTaskArcs(
                uiState = uiState,
                uiTimerState = { timerState }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GappedTaskArcsActivePreview() {
    SessionTimerTheme {
        Surface {
            val uiState = UiState.Ready(
                sessionTitle = "Session Title",
                totalDuration = 60.seconds,
                tasks = fakeTasks
            )

            GappedTaskArcs(
                uiState = uiState,
                uiTimerState = { uiTimerStateActive }
            )
        }
    }
}
