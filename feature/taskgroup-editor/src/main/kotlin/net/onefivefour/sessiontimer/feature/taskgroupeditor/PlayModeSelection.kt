package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.ui.R as UiR
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme


@Composable
internal fun PlayModeSelection(
    playMode: PlayMode,
    numberOfRandomTasks: Int,
    numberOfTasks: Int,
    gapSize: Dp,
    onPlayModeChanged: (PlayMode, Int) -> Unit,
) {

    Row(horizontalArrangement = Arrangement.spacedBy(gapSize)) {

        // Sequence Button
        PlayModeButton(
            iconRes = UiR.drawable.ic_play_mode_sequence,
            contentDescription = UiR.string.play_mode_sequence,
            isSelected = playMode == PlayMode.SEQUENCE,
            onClick = { onPlayModeChanged(PlayMode.SEQUENCE, numberOfRandomTasks) }
        )

        // Shuffle Button
        PlayModeButton(
            iconRes = UiR.drawable.ic_play_mode_shuffle,
            contentDescription = UiR.string.play_mode_shuffle,
            isSelected = playMode == PlayMode.N_TASKS_SHUFFLED,
            onClick = { onPlayModeChanged(PlayMode.N_TASKS_SHUFFLED, numberOfRandomTasks) }
        ) {
            ShuffleControls(
                numberOfRandomTasks = numberOfRandomTasks,
                numberOfTasks = numberOfTasks,
                onPlayModeChanged = { newPlayMode, numberOfRandomTasks ->
                    onPlayModeChanged(newPlayMode, numberOfRandomTasks)
                }
            )
        }

    }
}




@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PlayModeSelectionSequencePreview() {
    SessionTimerTheme {
        Surface {
            PlayModeSelection(
                playMode = PlayMode.SEQUENCE,
                numberOfRandomTasks = 5,
                numberOfTasks = 5,
                gapSize = 16.dp,
                onPlayModeChanged = { _, _ -> }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PlayModeSelectionShuffledPreview() {
    SessionTimerTheme {
        Surface {
            PlayModeSelection(
                playMode = PlayMode.N_TASKS_SHUFFLED,
                numberOfRandomTasks = 3,
                numberOfTasks = 5,
                gapSize = 16.dp,
                onPlayModeChanged = { _, _ -> }
            )
        }
    }
}