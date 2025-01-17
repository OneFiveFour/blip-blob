package net.onefivefour.sessiontimer.core.database.domain

import kotlin.time.Duration

interface TaskRepository {
    suspend fun newTask(title: String, durationInSeconds: Int, taskGroupId: Long)

    suspend fun updateTask(taskId: Long, title: String, duration: Duration, sortOrder: Int)

    suspend fun deleteTask(taskId: Long)

    suspend fun setTaskSortOrders(taskIds: List<Long>)

    suspend fun deleteTasksByTaskGroupId(taskGroupId: Long)
}
