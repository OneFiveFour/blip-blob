package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.Task

internal interface TaskDataSource {

    suspend fun insert(
        title: String,
        durationInSeconds: Long,
        sortOrder: Long,
        taskGroupId: Long
    )

    suspend fun update(
        taskId: Long,
        title: String,
        durationInSeconds: Long,
        sortOrder: Long
    )

    suspend fun setTaskSortOrders(taskIds: List<Long>)
    
    suspend fun deleteById(taskId: Long)

    suspend fun deleteByTaskGroupId(taskGroupId: Long)

    suspend fun deleteByIds(taskIds: List<Long>)
}
