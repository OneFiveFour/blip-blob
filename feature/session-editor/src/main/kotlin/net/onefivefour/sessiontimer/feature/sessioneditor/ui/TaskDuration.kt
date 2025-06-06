package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.ui.duration.DurationInput
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask

@Composable
internal fun TaskDuration(uiTask: UiTask, onDurationChanged: (Duration) -> Unit = { }) {
    val taskEditMode = LocalTaskEditMode.current
    val currentTaskEditMode = taskEditMode.value

    val isEditingDuration = currentTaskEditMode is TaskEditMode.TaskDuration &&
        currentTaskEditMode.initialTaskId == uiTask.id

    if (isEditingDuration) {
        DurationInput(
            initialDuration = uiTask.duration,
            requestFocus = true,
            onDurationEntered = onDurationChanged
        )
    } else {
        Text(
            modifier = Modifier.clickable {
                taskEditMode.value = TaskEditMode.TaskDuration(uiTask.id)
            },
            text = uiTask.duration.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface)
        )
    }
}
