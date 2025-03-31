package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.DenormalizedTaskGroupView
import net.onefivefour.sessiontimer.core.database.TaskGroup

internal interface TaskGroupDataSource {

    suspend fun insert(
        title: String,
        color: Long,
        onColor: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        defaultTaskDuration: Long,
        sessionId: Long
    ) : Long

    suspend fun getDenormalizedTaskGroup(taskGroupId: Long): Flow<List<DenormalizedTaskGroupView>>

    suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun deleteById(taskGroupId: Long)

    suspend fun setTaskGroupSortOrders(taskGroupIds: List<Long>)

    suspend fun deleteBySessionId(sessionId: Long)

    fun getLastInsertId(): Long

    suspend fun increaseNumberOfRandomTasks(taskGroupId: Long)

    suspend fun decreaseNumberOfRandomTasks(taskGroupId: Long)

    suspend fun setTaskGroupTitle(taskGroupId: Long, newTitle: String)

    suspend fun setTaskGroupPlayMode(
        taskGroupId: Long,
        newPlayMode: String,
        newNumberOfRandomTasks: Long
    )

    suspend fun setTaskGroupDefaultTaskDuration(taskGroupId: Long, newDuration: Long)

    suspend fun setTaskGroupColor(taskGroupId: Long, newColor: Long, newOnColor: Long)
}
