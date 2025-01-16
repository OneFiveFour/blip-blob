package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource

internal class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource
) : TaskRepository {

    override suspend fun newTask(
        title: String,
        durationInSeconds: Int,
        taskGroupId: Long
    ) {
        val sortOrder = 1L // TODO find correct sort order
        taskDataSource
            .insert(
                title = title,
                durationInSeconds = durationInSeconds.toLong(),
                sortOrder = sortOrder,
                taskGroupId = taskGroupId
            )

    }

    override suspend fun updateTask(
        taskId: Long,
        title: String,
        duration: Duration,
        sortOrder: Int
    ) =
        taskDataSource
            .update(
                taskId = taskId,
                title = title,
                durationInSeconds = duration.toLong(DurationUnit.SECONDS),
                sortOrder = sortOrder.toLong()
            )

    override suspend fun deleteTask(taskId: Long) = taskDataSource
        .deleteById(taskId)

    override suspend fun deleteTasksByTaskGroupId(taskGroupId: Long) = taskDataSource
        .deleteByTaskGroupId(taskGroupId)
}

private fun List<DatabaseTask>.toDomainTask(): List<DomainTask> {
    return map { databaseTask ->
        databaseTask.toDomainTask()
    }
}

internal fun DatabaseTask.toDomainTask(): DomainTask {
    return DomainTask(
        id = this.id,
        title = this.title,
        duration = this.durationInSeconds.seconds,
        sortOrder = this.sortOrder.toInt(),
        taskGroupId = this.taskGroupId
    )
}
