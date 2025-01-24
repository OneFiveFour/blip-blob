package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.ui.R as UiR
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme

@Composable
internal fun PlayModeSelection(
    playMode: PlayMode,
    numberOfRandomTasks: Int,
    numberOfTasks: Int,
    gapSize: Dp,
    onPlayModeChanged: (PlayMode, Int) -> Unit
) {

    Row(horizontalArrangement = Arrangement.spacedBy(gapSize)) {

        // Sequence Button
        Box(
            modifier = buttonModifier(playMode == PlayMode.SEQUENCE)
                .clickable { onPlayModeChanged(PlayMode.SEQUENCE, numberOfRandomTasks) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(TILE_SIZE),
                painter = painterResource(id = UiR.drawable.ic_play_mode_sequence),
                contentDescription = stringResource(id = UiR.string.play_mode_sequence),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        // Shuffle Button
        Box(
            modifier = buttonModifier(playMode == PlayMode.N_TASKS_SHUFFLED)
                .clickable { onPlayModeChanged(PlayMode.N_TASKS_SHUFFLED, numberOfRandomTasks) }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(TILE_SIZE)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    painter = painterResource(id = UiR.drawable.ic_play_mode_shuffle),
                    contentDescription = stringResource(UiR.string.play_mode_shuffle),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            if (numberOfRandomTasks > 1) {
                                onPlayModeChanged(PlayMode.N_TASKS_SHUFFLED, numberOfRandomTasks - 1)
                            }
                        }
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = stringResource(R.string.subtract_random_task),
                    tint = MaterialTheme.colorScheme.onSurface
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


                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            if (numberOfRandomTasks < numberOfTasks) {
                                onPlayModeChanged(PlayMode.N_TASKS_SHUFFLED, numberOfRandomTasks + 1)
                            }
                        }
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = stringResource(R.string.add_random_task),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(16.dp))
            }

        }
    }
}


@Composable
private fun buttonModifier(isSelected: Boolean): Modifier {

    val cornerShape = RoundedCornerShape(6.dp)

    return Modifier
        .clip(cornerShape)
        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
        .border(
            width = 2.dp,
            color = when (isSelected) {
                true -> MaterialTheme.colorScheme.onSurface
                else -> Color.Transparent
            },
            shape = cornerShape
        )
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