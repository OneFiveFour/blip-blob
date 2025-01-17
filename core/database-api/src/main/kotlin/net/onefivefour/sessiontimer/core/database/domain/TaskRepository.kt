package net.onefivefour.sessiontimer.core.database.domain

interface TaskRepository {
    suspend fun newTask(title: String, durationInSeconds: Int, taskGroupId: Long)

    suspend fun setTaskTitle(taskId: Long, title: String)

    suspend fun deleteTask(taskId: Long)

    suspend fun setTaskSortOrders(taskIds: List<Long>)

    suspend fun deleteTasksByTaskGroupId(taskGroupId: Long)
}
