package net.onefivefour.sessiontimer.feature.sessioneditor.ui

internal sealed class TaskEditMode {

    data object None : TaskEditMode()
    data class TaskTitle(val initialTaskId: Long) : TaskEditMode()
    data class TaskDuration(val initialTaskId: Long) : TaskEditMode()

    val isEditing: Boolean
        get() = this !is None

}