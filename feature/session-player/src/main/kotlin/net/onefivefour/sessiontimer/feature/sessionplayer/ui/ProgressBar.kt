package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun ProgressBar(
    modifier: Modifier = Modifier,
    uiState: UiState.Ready,
    uiTimerState: UiTimerState
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(24.dp)
    ) {
        // grey background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surfaceDim)
        )

        // white background line
        CircluarProgressNeon(
            modifier = Modifier.padding(16.dp),
            strokeColor = MaterialTheme.colorScheme.background,
            strokeWidth = 7.dp,
            glowColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
            glowWidth = 14.dp,
            glowRadius = 10.dp
        )

        // outer task progress
        TaskProgressArc(
            modifier = Modifier.padding(16.dp),
            uiTimerState = { uiTimerState },
            task = null,
            width = 11.dp,
            startAngle = 0f,
            endAngle = 360f
        )

        // inner task arcs
        GappedTaskArcs(
            uiState = uiState,
            uiTimerState = { uiTimerState }
        )

        // Time
        CountDown(
            modifier = Modifier
                .width(200.dp)
                .align(Alignment.Center),
            uiTimerState = uiTimerState
        )
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionProgressBarPreview() {
    SessionTimerTheme {
        Surface {
            ProgressBar(
                uiState = UiState.Ready(
                    sessionTitle = "Session Title",
                    tasks = fakeTasks,
                    totalDuration = 60.seconds
                ),
                uiTimerState = uiTimerStateActive
            )
        }
    }
}