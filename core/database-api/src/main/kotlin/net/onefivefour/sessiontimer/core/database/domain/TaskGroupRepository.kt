package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import kotlin.time.Duration

interface TaskGroupRepository {
    suspend fun newTaskGroup(
        title: String,
        color: Long,
        onColor: Long,
        playMode: PlayMode,
        numberOfRandomTasks: Int,
        defaultTaskDuration: Duration,
        sessionId: Long
    )

    suspend fun getTaskGroupById(taskGroupId: Long): Flow<TaskGroup>

    suspend fun getTaskGroupBySessionId(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun decreaseNumberOfRandomTasks(taskGroupId: Long)

    suspend fun increaseNumberOfRandomTasks(taskGroupId: Long)

    suspend fun setTaskGroupSortOrders(taskGroupIds: List<Long>)

    suspend fun deleteTaskGroupById(taskGroupId: Long)


    suspend fun setTaskGroupTitle(taskGroupId: Long, newTitle: String)

    suspend fun setTaskGroupPlayMode(taskGroupId: Long, newPlayMode: PlayMode, newNumberOfRandomTasks: Int)

    suspend fun setTaskGroupDefaultTaskDuration(taskGroupId: Long, newDefaultTaskDuration: Duration)

    suspend fun setTaskGroupColor(taskGroupId: Long, newColor: Int, newOnColor: Int)

    fun getLastInsertId(): Long
}
