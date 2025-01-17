package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
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

    override suspend fun updateTask(
        taskId: Long,
        title: String,
        duration: Duration,
        sortOrder: Int
    ) = taskDataSource
        .update(
            taskId = taskId,
            title = title,
            durationInSeconds = duration.toLong(DurationUnit.SECONDS),
            sortOrder = sortOrder.toLong()
        )

    override suspend fun setTaskSortOrders(taskIds: List<Long>) = taskDataSource
        .setTaskSortOrders(taskIds)

    override suspend fun deleteTask(taskId: Long) = taskDataSource
        .deleteById(taskId)

    override suspend fun deleteTasksByTaskGroupId(taskGroupId: Long) = taskDataSource
        .deleteByTaskGroupId(taskGroupId)
}
