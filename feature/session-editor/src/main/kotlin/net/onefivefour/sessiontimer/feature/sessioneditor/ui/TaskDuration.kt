package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.onefivefour.sessiontimer.core.common.extensions.toDuration
import net.onefivefour.sessiontimer.core.common.extensions.toSixDigitsString
import net.onefivefour.sessiontimer.core.ui.duration.DurationInput
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import kotlin.time.Duration

@Composable
internal fun TaskDuration(
    uiTask: UiTask,
    onDurationChanged: (Duration) -> Unit = { }
) {

    val taskEditMode = LocalTaskEditMode.current

    val currentTaskEditMode = taskEditMode.value
    if (currentTaskEditMode is TaskEditMode.TaskDuration && currentTaskEditMode.initialTaskId == uiTask.id) {
        DurationInput(
            initialDuration = uiTask.duration.toSixDigitsString(),
            onDurationEntered = { newDurationString ->
                val newDuration = newDurationString.toDuration()
                onDurationChanged(newDuration)
            }
        )
    } else {
        Text(
            modifier = Modifier.clickable {
                taskEditMode.value = TaskEditMode.TaskDuration(uiTask.id)
            },
            text = uiTask.duration.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )
    }
}