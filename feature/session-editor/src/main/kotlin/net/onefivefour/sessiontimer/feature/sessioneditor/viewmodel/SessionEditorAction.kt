package net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel

import kotlin.time.Duration

internal sealed class SessionEditorAction {
    data object CreateTaskGroup : SessionEditorAction()
    data class DeleteTaskGroup(val taskGroupId: Long) : SessionEditorAction()
    data class UpdateTaskGroupSortOrders(val taskGroupIds: List<Long>) : SessionEditorAction()
    data class CreateTask(val taskGroupId: Long) : SessionEditorAction()
    data class DeleteTask(val taskId: Long, val taskGroupId: Long) : SessionEditorAction()
    data class UpdateTaskSortOrders(val taskIds: List<Long>) : SessionEditorAction()
    data class SetTaskTitle(val taskId: Long, val newTitle: String) : SessionEditorAction()
    data class SetSessionTitle(val newTitle: String) : SessionEditorAction()
    data class SetTaskDuration(val taskId: Long, val newDuration: Duration) : SessionEditorAction()
}
