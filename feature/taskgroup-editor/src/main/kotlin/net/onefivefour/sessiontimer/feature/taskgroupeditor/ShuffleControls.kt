package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.ui.R

@Composable
internal fun ShuffleControls(
    numberOfRandomTasks: Int,
    numberOfTasks: Int,
    onPlayModeChanged: (PlayMode, Int?) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier
                .size(TILE_SIZE_DP)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            painter = painterResource(id = R.drawable.ic_play_mode_shuffle),
            contentDescription = stringResource(R.string.play_mode_shuffle),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    if (numberOfRandomTasks > 1) {
                        onPlayModeChanged(
                            PlayMode.N_TASKS_SHUFFLED,
                            numberOfRandomTasks - 1
                        )
                    }
                }
                .padding(10.dp),
            painter = painterResource(id = net.onefivefour.sessiontimer.core.taskgroupeditor.R.drawable.ic_minus),
            contentDescription = stringResource(net.onefivefour.sessiontimer.core.taskgroupeditor.R.string.subtract_random_task),
            tint = MaterialTheme.colorScheme.onSurface
        )


        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = when (numberOfRandomTasks) {
                numberOfTasks -> stringResource(net.onefivefour.sessiontimer.core.taskgroupeditor.R.string.all).uppercase()
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
                        onPlayModeChanged(
                            PlayMode.N_TASKS_SHUFFLED,
                            numberOfRandomTasks + 1
                        )
                    }
                }
                .padding(10.dp),
            painter = painterResource(id = net.onefivefour.sessiontimer.core.taskgroupeditor.R.drawable.ic_plus),
            contentDescription = stringResource(net.onefivefour.sessiontimer.core.taskgroupeditor.R.string.add_random_task),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}