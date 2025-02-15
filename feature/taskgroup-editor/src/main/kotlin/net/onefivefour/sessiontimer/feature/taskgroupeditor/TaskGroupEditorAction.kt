package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode

internal sealed interface TaskGroupEditorAction {
    data class SetTaskGroupTitle(val newTitle: String) : TaskGroupEditorAction
    data class SetColor(val newColor: Color, val newOnColor: Color) : TaskGroupEditorAction
    data class SetPlayMode(
        val newPlayMode: PlayMode,
        val newNumberOfRandomTasks: Int
    ) : TaskGroupEditorAction
    data class OnDurationEntered(val newDuration: Duration) : TaskGroupEditorAction
}
