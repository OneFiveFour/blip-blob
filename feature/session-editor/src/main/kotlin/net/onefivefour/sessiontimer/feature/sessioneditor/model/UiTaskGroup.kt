package net.onefivefour.sessiontimer.feature.sessioneditor.model

import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import kotlin.time.Duration

internal data class UiTaskGroup(
    val id: Long,
    val title: String,
    val color: Color,
    val onColor: Color,
    val playMode: PlayMode,
    val numberOfRandomTasks: Int = 0,
    val defaultTaskDuration: Duration,
    val tasks: List<UiTask>
)

internal fun List<TaskGroup>.toUiTaskGroups() = this.map {
    it.toUiTaskGroup()
}

internal fun TaskGroup.toUiTaskGroup() = UiTaskGroup(
    id = this.id,
    title = this.title,
    color = Color(this.color),
    onColor = Color(this.onColor),
    playMode = this.playMode,
    numberOfRandomTasks = this.numberOfRandomTasks,
    defaultTaskDuration = this.defaultTaskDuration,
    tasks = this.tasks.toUiTasks()
)
