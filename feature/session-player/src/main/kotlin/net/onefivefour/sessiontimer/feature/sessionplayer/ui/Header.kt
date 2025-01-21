package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.R
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun Header(
    modifier: Modifier = Modifier,
    uiState: UiState.Ready,
    uiTimerState: UiTimerState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = uiState.sessionTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayMedium
        )

        val title = when (uiTimerState) {
            is UiTimerState.Active -> uiTimerState.currentTask?.taskTitle ?: ""
            UiTimerState.Finished -> stringResource(R.string.finished)
            is UiTimerState.Initial -> return
        }

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionHeaderPreview() {
    SessionTimerTheme {
        Surface {
            Header(
                uiState = UiState.Ready(
                    sessionTitle = "Test Session",
                    totalDuration = 60.seconds,
                    tasks = fakeTasks
                ),
                uiTimerState = UiTimerState.Initial()
            )
        }
    }
}