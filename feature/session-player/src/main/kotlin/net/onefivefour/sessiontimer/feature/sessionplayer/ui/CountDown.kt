package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.text.format.DateUtils
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState

@Composable
internal fun CountDown(
    modifier: Modifier = Modifier,
    uiTimerState: UiTimerState
) {
    if (uiTimerState !is UiTimerState.Active || uiTimerState.currentTask == null) {
        return
    }
    val remainingDuration = uiTimerState.currentTask.taskDuration - uiTimerState.elapsedTaskDuration
    val formattedRemainer =DateUtils.formatElapsedTime(remainingDuration.inWholeSeconds + 1)
        .replace("(", "")
        .replace(")", "")
    Text(
        modifier = modifier,
        text = formattedRemainer,
        style = MaterialTheme.typography.labelLarge,
        textAlign = TextAlign.Center,
        maxLines = 1
    )
}

@Preview
@Composable
private fun CountDownPreview() {
    SessionTimerTheme {
        Surface {
            CountDown(
                uiTimerState = uiTimerStateActive
            )
        }
    }
}