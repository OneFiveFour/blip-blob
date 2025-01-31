package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import java.util.Locale
import kotlin.time.Duration

internal data class UiTaskGroup(
    val id: Long,
    val title: String,
    val color: Color,
    val playMode: PlayMode,
    val numberOfRandomTasks: Int = 0,
    val defaultTaskDuration: String,
    val sortOrder: Int,
    val tasks: List<UiTask>
)

internal fun TaskGroup.toUiTaskGroup() = UiTaskGroup(
    id = this.id,
    title = this.title,
    color = Color(this.color),
    playMode = this.playMode,
    numberOfRandomTasks = this.numberOfRandomTasks,
    defaultTaskDuration = this.defaultTaskDuration.toUiTaskDuration(),
    sortOrder = this.sortOrder,
    tasks = this.tasks.toUiTasks()
)

internal fun Duration.toUiTaskDuration() = toComponents { h, m, s, _ ->
    String.format(Locale.getDefault(), "%02d%02d%02d", h, m, s)
}
