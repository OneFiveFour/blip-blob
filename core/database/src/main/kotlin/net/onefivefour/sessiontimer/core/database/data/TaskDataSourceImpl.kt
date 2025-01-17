package net.onefivefour.sessiontimer.core.database.data

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
                queries.new(
                    id = null,
                    title = title,
                    durationInSeconds = durationInSeconds,
                    sortOrder = maxSortOrder + 1,
                    taskGroupId = taskGroupId
                )
            }
        }
    }

    override suspend fun update(
        taskId: Long,
        title: String,
        durationInSeconds: Long,
        sortOrder: Long
    ) {
        withContext(dispatcher) {
            queries.update(
                title = title,
                durationInSeconds = durationInSeconds,
                sortOrder = sortOrder,
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
