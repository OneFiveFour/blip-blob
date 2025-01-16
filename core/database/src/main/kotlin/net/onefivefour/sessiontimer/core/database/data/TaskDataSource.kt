package net.onefivefour.sessiontimer.core.database.data

internal interface TaskDataSource {

    suspend fun insert(
        title: String,
        durationInSeconds: Long,
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
