package net.onefivefour.sessiontimer.feature.sessioneditor.ui

internal sealed class TaskEditMode {

    data object None : TaskEditMode()
    data class TaskTitle(val taskId: Long?) : TaskEditMode()
    data class TaskDuration(val taskId: Long?) : TaskEditMode()

    val isEditing: Boolean
        get() = this !is None

    fun isEditing(taskId: Long): Boolean {
        return when (this) {
            is TaskTitle -> this.taskId == taskId
            is TaskDuration -> this.taskId == taskId
            else -> false
        }
    }
}