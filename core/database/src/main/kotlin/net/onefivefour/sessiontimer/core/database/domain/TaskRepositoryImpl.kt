package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource

internal class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource
) : TaskRepository {

    override suspend fun newTask(title: String, durationInSeconds: Int, taskGroupId: Long) {
        taskDataSource
            .insert(
                title = title,
                durationInSeconds = durationInSeconds.toLong(),
                taskGroupId = taskGroupId
            )
    }

    override suspend fun setTaskTitle(
        taskId: Long,
        title: String
    ) = taskDataSource
        .setTaskTitle(
            taskId = taskId,
            title = title
        )

    override suspend fun setTaskSortOrders(taskIds: List<Long>) = taskDataSource
        .setTaskSortOrders(taskIds)

    override suspend fun deleteTask(taskId: Long) = taskDataSource
        .deleteById(taskId)

    override suspend fun deleteTasksByTaskGroupId(taskGroupId: Long) = taskDataSource
        .deleteByTaskGroupId(taskGroupId)
}
