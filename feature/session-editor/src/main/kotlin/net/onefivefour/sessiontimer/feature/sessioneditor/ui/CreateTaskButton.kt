package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun CreateTaskButton(
    defaultTaskDuration: Duration,
    onCreateTask: () -> Unit
) {
    Row(
        modifier = Modifier
            .size(TASK_ITEM_HEIGHT)
            .clickable { onCreateTask() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.new_task),
            style = MaterialTheme.typography.titleSmall
                .copy(color = MaterialTheme.colorScheme.onSurface
                    .copy(alpha = 0.5f)),
        )

        Text(
            text = defaultTaskDuration.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface
                    .copy(alpha = 0.5f)),
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun CreateTaskButtonPreview() {
    SessionTimerTheme {
        Surface {
            CreateTaskButton(
                defaultTaskDuration = 90.seconds,
                onCreateTask = { }
            )
        }
    }
}