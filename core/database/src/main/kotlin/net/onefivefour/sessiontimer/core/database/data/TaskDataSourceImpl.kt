package net.onefivefour.sessiontimer.core.database.data

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import net.onefivefour.sessiontimer.core.database.TaskQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class TaskDataSourceImpl @Inject constructor(
    private val queries: TaskQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TaskDataSource {

    override suspend fun insert(title: String, durationInSeconds: Long, taskGroupId: Long) {
        withContext(dispatcher) {
            queries.transaction {
                val maxSortOrder = queries.findMaxSortOrder(taskGroupId).executeAsOne().MAX ?: 0L
                val createdAt = Clock.System.now().toEpochMilliseconds()
                queries.new(
                    id = null,
                    title = title,
                    durationInSeconds = durationInSeconds,
                    sortOrder = maxSortOrder + 1,
                    taskGroupId = taskGroupId,
                    createdAt = createdAt
                )
            }
        }
    }

    override suspend fun setTaskTitle(taskId: Long, title: String) {
        withContext(dispatcher) {
            queries.setTaskTitle(
                title = title,
                id = taskId
            )
        }
    }

    override suspend fun setTaskSortOrders(taskIds: List<Long>) {
        withContext(dispatcher) {
            queries.transaction {
                taskIds.forEachIndexed { index, taskId ->
                    queries.setSortOrder(index.toLong(), taskId)
                }
            }
        }
    }

    override suspend fun setTaskDuration(taskId: Long, durationInSeconds: Long) {
        withContext(dispatcher) {
            queries.setDuration(
                taskId = taskId,
                durationInSeconds = durationInSeconds
            )
        }
    }

    override suspend fun deleteById(taskId: Long) {
        withContext(dispatcher) {
            queries.deleteById(taskId)
        }
    }

    override suspend fun deleteByIds(taskIds: List<Long>) {
        withContext(dispatcher) {
            queries.deleteByIds(taskIds)
        }
    }

    override suspend fun deleteByTaskGroupId(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.deleteByTaskGroupId(taskGroupId)
        }
    }
}
