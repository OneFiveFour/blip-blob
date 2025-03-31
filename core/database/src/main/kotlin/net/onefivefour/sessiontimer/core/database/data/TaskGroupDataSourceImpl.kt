package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.database.DenormalizedTaskGroupView
import net.onefivefour.sessiontimer.core.database.TaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class TaskGroupDataSourceImpl @Inject constructor(
    private val queries: TaskGroupQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TaskGroupDataSource {

    override suspend fun insert(
        title: String,
        color: Long,
        onColor: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        defaultTaskDuration: Long,
        sessionId: Long
    ) : Long {
        return withContext(dispatcher) {
            queries.transactionWithResult {
                val maxSortOrder = queries.findMaxSortOrder(sessionId).executeAsOne().MAX ?: 0L
                queries.new(
                    id = null,
                    title = title,
                    color = color,
                    onColor = onColor,
                    playMode = playMode,
                    numberOfRandomTasks = numberOfRandomTasks,
                    defaultTaskDuration = defaultTaskDuration,
                    sortOrder = maxSortOrder + 1,
                    sessionId = sessionId
                )
                queries.getLastInsertRowId().executeAsOne()
            }
        }
    }

    override suspend fun getDenormalizedTaskGroup(
        taskGroupId: Long
    ): Flow<List<DenormalizedTaskGroupView>> {
        return withContext(dispatcher) {
            queries.denormalizedTaskGroupView(taskGroupId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>> {
        return withContext(dispatcher) {
            queries.getBySessionId(sessionId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun setTaskGroupSortOrders(taskGroupIds: List<Long>) {
        withContext(dispatcher) {
            queries.transaction {
                taskGroupIds.forEachIndexed { index, taskGroupId ->
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

    override suspend fun increaseNumberOfRandomTasks(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.increaseNumberOfRandomTasks(taskGroupId)
        }
    }

    override suspend fun decreaseNumberOfRandomTasks(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.decreaseNumberOfRandomTasks(taskGroupId)
        }
    }

    override suspend fun setTaskGroupTitle(taskGroupId: Long, newTitle: String) {
        withContext(dispatcher) {
            queries.setTitle(newTitle, taskGroupId)
        }
    }

    override suspend fun setTaskGroupPlayMode(
        taskGroupId: Long,
        newPlayMode: String,
        newNumberOfRandomTasks: Long
    ) {
        withContext(dispatcher) {
            queries.setPlayMode(newPlayMode, newNumberOfRandomTasks, taskGroupId)
        }
    }

    override suspend fun setTaskGroupDefaultTaskDuration(taskGroupId: Long, newDuration: Long) {
        withContext(dispatcher) {
            queries.setDefaultTaskDuration(newDuration, taskGroupId)
        }
    }

    override suspend fun setTaskGroupColor(taskGroupId: Long, newColor: Long, newOnColor: Long) {
        withContext(dispatcher) {
            queries.setColor(newColor, newOnColor, taskGroupId)
        }
    }
}
