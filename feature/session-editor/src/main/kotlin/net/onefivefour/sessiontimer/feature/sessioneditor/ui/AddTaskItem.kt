package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.feature.sessioneditor.R
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction
import kotlin.time.Duration

@Composable
internal fun AddTaskItem(
    modifier: Modifier = Modifier,
    defaultDuration: Duration,
    taskGroupId: Long,
    onAction: (SessionEditorAction) -> Unit,
) {

    Row(
        modifier = modifier
            .alpha(0.4f)
            .height(64.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onAction(SessionEditorAction.CreateTask(taskGroupId)) },
        verticalAlignment = Alignment.CenterVertically
    ) {

        DragHandler()

        Spacer(Modifier.width(12.dp))

        Text(
            modifier = Modifier
                .weight(1f),
            text = stringResource(R.string.add_task),
            style = MaterialTheme.typography.titleSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )

        Text(
            text = defaultDuration.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreview() {
    SessionTimerTheme {
        Surface {
            TaskItem(
                uiTask = uiTask3,
                onAction = { }
            )
        }
    }
}
