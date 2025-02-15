package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.SessionTimerViewModel
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState

@Composable
internal fun SessionPlayerReady(uiState: UiState.Ready) {
    val timerViewModel: SessionTimerViewModel = hiltViewModel()
    val uiTimerState by timerViewModel.uiTimerState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            timerViewModel.onDispose()
        }
    }

    SessionPlayerReadyInternal(
        uiState = uiState,
        uiTimerState = uiTimerState,
        onAction = timerViewModel::onAction
    )
}

@Composable
private fun SessionPlayerReadyInternal(
    uiState: UiState.Ready,
    uiTimerState: UiTimerState,
    onAction: (SessionPlayerAction) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Header(
            modifier = Modifier.align(Alignment.TopCenter),
            uiState = uiState,
            uiTimerState = uiTimerState
        )

        ProgressBar(
            modifier = Modifier.align(Alignment.Center),
            uiState = uiState,
            uiTimerState = uiTimerState
        )

        Controls(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            uiTimerState = { uiTimerState },
            onAction = onAction
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionPlayerPreview() {
    SessionTimerTheme {
        Surface {
            val uiState = UiState.Ready(
                sessionTitle = "Session Title",
                tasks = fakeTasks,
                totalDuration = 60.seconds
            )
            SessionPlayerReadyInternal(
                uiState = uiState,
                uiTimerState = uiTimerStateActive,
                onAction = { }
            )
        }
    }
}
