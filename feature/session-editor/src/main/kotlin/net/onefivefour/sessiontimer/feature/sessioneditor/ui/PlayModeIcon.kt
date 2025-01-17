package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup

@Composable
internal fun RowScope.PlayModeIcon(uiTaskGroup: UiTaskGroup) {
    val playModeIconRes = when (uiTaskGroup.playMode) {
        PlayMode.SEQUENCE -> R.drawable.ic_play_mode_sequence
        PlayMode.N_TASKS_SHUFFLED -> R.drawable.ic_play_mode_shuffle
    }

    val playModeStringRes = when (uiTaskGroup.playMode) {
        PlayMode.SEQUENCE -> R.string.play_mode_sequence
        PlayMode.N_TASKS_SHUFFLED -> R.string.play_mode_shuffle
    }


    Icon(
        painter = painterResource(id = playModeIconRes),
        tint = MaterialTheme.colorScheme.onSurface,
        contentDescription = stringResource(id = playModeStringRes)
    )

    val showBadge = uiTaskGroup.playMode == PlayMode.N_TASKS_SHUFFLED &&
            uiTaskGroup.numberOfRandomTasks < uiTaskGroup.tasks.size

    if (showBadge) {

        val badgeText = when {
            uiTaskGroup.numberOfRandomTasks < 100 -> {
                uiTaskGroup.numberOfRandomTasks.toString()
            }

            else -> stringResource(id = net.onefivefour.sessiontimer.feature.sessioneditor.R.string.max_badge_value)
        }

        Box(
            modifier = Modifier
                .offset(x = (-14).dp, y = (-2).dp)
                .size(16.dp)
                .background(
                    color = uiTaskGroup.color,
                    shape = CircleShape
                )
                .align(Alignment.Bottom),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = badgeText,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp
            )
        }
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PlayModeIconSequencePreview() {
    SessionTimerTheme {
        Surface {
            Row {
                PlayModeIcon(
                    fakeUiTaskGroup()
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PlayModeIconShuffleNPreview() {
    SessionTimerTheme {
        Surface {
            Row {
                PlayModeIcon(
                    fakeUiTaskGroup()
                        .copy(
                            playMode = PlayMode.N_TASKS_SHUFFLED,
                            numberOfRandomTasks = 2
                        )
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PlayModeIconShuffleAllPreview() {
    SessionTimerTheme {
        Surface {
            Row {
                val tasks = fakeUiTaskGroup().tasks.size
                PlayModeIcon(
                    fakeUiTaskGroup()
                        .copy(
                            playMode = PlayMode.N_TASKS_SHUFFLED,
                            numberOfRandomTasks =tasks
                        )
                )
            }
        }
    }
}