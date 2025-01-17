package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.components.button.drawGlowingSides
import net.onefivefour.sessiontimer.core.ui.components.dragger.Dragger
import net.onefivefour.sessiontimer.feature.sessioneditor.R
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import net.onefivefour.sessiontimer.core.ui.R as UiR

@Composable
internal fun TaskGroupSummary(
    uiTaskGroup: UiTaskGroup,
    onEditTaskGroup: (Long) -> Unit,
    onCollapseChanged: (Boolean) -> Unit,
    isCollapsed: Boolean,
) {

    val glowColor = MaterialTheme.customColors.surfaceGlow
    val backgroundColor = MaterialTheme.colorScheme.surface

    Row(
        modifier = Modifier
            .height(TASK_GROUP_ITEM_HEIGHT)
            .drawWithContent {
                drawGlowingSides(
                    glowColor = glowColor,
                    backgroundColor = backgroundColor
                )
            }
            .padding(16.dp)
            .clickable { onEditTaskGroup(uiTaskGroup.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Dragger()

        Box {
            val playModeIconRes = when (uiTaskGroup.playMode) {
                PlayMode.SEQUENCE -> UiR.drawable.ic_play_mode_sequence
                PlayMode.N_TASKS_SHUFFLED -> UiR.drawable.ic_play_mode_shuffle
            }

            val playModeStringRes = when (uiTaskGroup.playMode) {
                PlayMode.SEQUENCE -> UiR.string.play_mode_sequence
                PlayMode.N_TASKS_SHUFFLED -> UiR.string.play_mode_shuffle
            }


            Icon(
                painter = painterResource(id = playModeIconRes),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(id = playModeStringRes)
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            text = uiTaskGroup.title,
            style = MaterialTheme.typography.titleMedium
        )

        val collapseIconRes = when {
            isCollapsed -> R.drawable.ic_collapse_down
            else -> R.drawable.ic_collapse_up
        }

        Icon(
            modifier = Modifier
                .padding(4.dp)
                .clickable { onCollapseChanged(!isCollapsed) },
            painter = painterResource(id = collapseIconRes),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = stringResource(id = R.string.collapse)
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupSummaryPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupSummary(
                uiTaskGroup = fakeUiTaskGroup(),
                onEditTaskGroup = { },
                onCollapseChanged = { },
                isCollapsed = false
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupSummaryCollapsedPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupSummary(
                uiTaskGroup = fakeUiTaskGroup().copy(
                    playMode = PlayMode.N_TASKS_SHUFFLED,
                    numberOfRandomTasks = 2
                ),
                onEditTaskGroup = { },
                onCollapseChanged = { },
                isCollapsed = true
            )
        }
    }
}