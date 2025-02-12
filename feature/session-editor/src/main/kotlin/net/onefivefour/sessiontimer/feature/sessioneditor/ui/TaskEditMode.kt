package net.onefivefour.sessiontimer.feature.sessioneditor.ui

internal sealed class TaskEditMode {

    data object None : TaskEditMode()
    data object TaskTitle : TaskEditMode()
    data object TaskDuration : TaskEditMode()

    val isEditing: Boolean
        get() = this !is None

}