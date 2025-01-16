package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.DenormalizedTaskGroupView
import net.onefivefour.sessiontimer.core.database.TaskGroup

internal interface TaskGroupDataSource {

    suspend fun insert(
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        sortOrder: Long,
        sessionId: Long
    )

    suspend fun getDenormalizedTaskGroup(taskGroupId: Long): Flow<List<DenormalizedTaskGroupView>>

    suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun deleteById(taskGroupId: Long)

    suspend fun update(
        taskGroupId: Long,
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        sortOrder: Long
    )

    suspend fun setTaskGroupSortOrders(taskGroupIds: List<Long>)

    suspend fun deleteBySessionId(sessionId: Long)

    fun getLastInsertId(): Long
}
