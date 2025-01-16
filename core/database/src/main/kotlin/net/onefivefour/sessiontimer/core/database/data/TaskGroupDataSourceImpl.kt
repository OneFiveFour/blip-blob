package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.database.DenormalizedTaskGroupView
import net.onefivefour.sessiontimer.core.database.TaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher
import javax.inject.Inject

internal class TaskGroupDataSourceImpl @Inject constructor(
    private val queries: TaskGroupQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : TaskGroupDataSource {

    override suspend fun insert(
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        sessionId: Long,
    ) {
        withContext(dispatcher) {
            queries.transaction {
                val maxSortOrder = queries.findMaxSortOrder(sessionId).executeAsOne().MAX ?: 0L
                queries.new(
                    id = null,
                    title = title,
                    color = color,
                    playMode = playMode,
                    numberOfRandomTasks = numberOfRandomTasks,
                    sortOrder = maxSortOrder + 1,
                    sessionId = sessionId
                )
            }
        }
    }

    override suspend fun getDenormalizedTaskGroup(taskGroupId: Long): Flow<List<DenormalizedTaskGroupView>> {
        return withContext(dispatcher) {
            queries.denormalizedTaskGroupView(taskGroupId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>> {
        return withContext(dispatcher) {
            queries.getBySessionId(sessionId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun update(
        taskGroupId: Long,
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        sortOrder: Long,
    ) {
        withContext(dispatcher) {
            queries.update(
                id = taskGroupId,
                title = title,
                color = color,
                playMode = playMode,
                numberOfRandomTasks = numberOfRandomTasks,
                sortOrder = sortOrder
            )
        }
    }

    override suspend fun setTaskGroupSortOrders(taskGroupIds: List<Long>) {
        withContext(dispatcher) {
            queries.transaction {
                taskGroupIds.forEachIndexed { index, taskGroupId ->
                    println("+++ setting taskGroupId $taskGroupId to sort order $index")
                    queries.setSortOrder(index.toLong(), taskGroupId)
                }
            }
        }
    }

    override suspend fun deleteById(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.deleteById(taskGroupId)
        }
    }

    override suspend fun deleteBySessionId(sessionId: Long) {
        withContext(dispatcher) {
            queries.deleteBySessionId(sessionId)
        }
    }

    override fun getLastInsertId(): Long {
        return queries.getLastInsertRowId().executeAsOne()
    }
}
