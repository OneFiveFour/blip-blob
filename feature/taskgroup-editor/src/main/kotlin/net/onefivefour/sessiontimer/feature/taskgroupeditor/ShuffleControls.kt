package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R as UiR

@Composable
internal fun ShuffleControls(
    numberOfRandomTasks: Int,
    numberOfTasks: Int,
    onPlayModeChanged: (PlayMode, Int) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // SEQUENCED
        Icon(
            modifier = Modifier
                .size(TILE_SIZE_DP)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            painter = painterResource(id = UiR.drawable.ic_play_mode_shuffle),
            contentDescription = stringResource(UiR.string.play_mode_shuffle),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(16.dp))

        // SHUFFLED
        ShuffleButton(
            iconRes = R.drawable.ic_minus,
            contentDescriptionRes = R.string.subtract_random_task,
            isEnabled = numberOfRandomTasks > 1,
            onClick = {
                onPlayModeChanged(PlayMode.N_TASKS_SHUFFLED, numberOfRandomTasks - 1)
            }
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = when (numberOfRandomTasks) {
                numberOfTasks -> stringResource(R.string.all).uppercase()
                else -> numberOfRandomTasks.toString()
            },
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        ShuffleButton(
            iconRes = R.drawable.ic_plus,
            contentDescriptionRes = R.string.add_random_task,
            isEnabled = numberOfRandomTasks < numberOfTasks,
            onClick = {
                onPlayModeChanged(PlayMode.N_TASKS_SHUFFLED, numberOfRandomTasks + 1)
            }
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ShuffleControlsPreview() {
    SessionTimerTheme {
        Surface {
            ShuffleControls(
                numberOfRandomTasks = 3,
                numberOfTasks = 5,
                onPlayModeChanged = { _, _ -> }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ShuffleControlsAllSelectedPreview() {
    SessionTimerTheme {
        Surface {
            ShuffleControls(
                numberOfRandomTasks = 5,
                numberOfTasks = 5,
                onPlayModeChanged = { _, _ -> }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ShuffleControlsMinimumPreview() {
    SessionTimerTheme {
        Surface {
            ShuffleControls(
                numberOfRandomTasks = 1,
                numberOfTasks = 5,
                onPlayModeChanged = { _, _ -> }
            )
        }
    }
}
