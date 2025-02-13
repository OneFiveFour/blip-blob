package net.onefivefour.sessiontimer.core.database.domain

import kotlin.time.Duration

interface TaskRepository {
    suspend fun newTask(title: String, duration: Duration, taskGroupId: Long)

    suspend fun setTaskTitle(taskId: Long, title: String)

    suspend fun deleteTask(taskId: Long)

    suspend fun setTaskSortOrders(taskIds: List<Long>)

    suspend fun deleteTasksByTaskGroupId(taskGroupId: Long)

    suspend fun setTaskDuration(taskId: Long, duration: Duration)
}
