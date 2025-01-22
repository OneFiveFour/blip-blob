package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import kotlin.time.Duration

internal data class UiTaskGroup(
    val id: Long,
    val title: String,
    val color: Color,
    val playMode: PlayMode,
    val numberOfRandomTasks: Int = 0,
    val defaultTaskDuration: Duration,
    val sortOrder: Int,
    val tasks: List<UiTask>
)

internal fun TaskGroup.toUiTaskGroup() = UiTaskGroup(
    id = this.id,
    title = this.title,
    color = Color(this.color),
    playMode = this.playMode,
    numberOfRandomTasks = this.numberOfRandomTasks,
    defaultTaskDuration = this.defaultTaskDuration,
    sortOrder = this.sortOrder,
    tasks = this.tasks.toUiTasks()
)
