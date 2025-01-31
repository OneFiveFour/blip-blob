package net.onefivefour.sessiontimer.feature.taskgroupeditor

import kotlin.time.Duration

internal data class UiTaskDuration(
    val hours: String,
    val minutes: String,
    val seconds: String
)

internal fun Duration.toUiTaskDuration(): UiTaskDuration {
    val totalSeconds = this.inWholeSeconds

    val hours = (totalSeconds / 3600).toString().padStart(2, '0')
    val minutes = ((totalSeconds % 3600) / 60).toString().padStart(2, '0')
    val seconds = (totalSeconds % 60).toString().padStart(2, '0')

    return UiTaskDuration(hours, minutes, seconds)
}