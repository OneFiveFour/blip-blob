package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode

internal sealed class TaskGroupEditorAction {
    data class SetTitle(val newTitle: String) : TaskGroupEditorAction()
    data class SetColor(val newColor: Color) : TaskGroupEditorAction()
    data class SetPlayMode(val newPlayMode: PlayMode, val newNumberOfRandomTasks: Int) : TaskGroupEditorAction()
}