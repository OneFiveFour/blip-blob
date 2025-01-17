package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup as DomainTaskGroup
import net.onefivefour.sessiontimer.core.database.DenormalizedTaskGroupView
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource

internal class TaskGroupRepositoryImpl @Inject constructor(
    private val taskGroupDataSource: TaskGroupDataSource
) : TaskGroupRepository {

    override suspend fun newTaskGroup(
        title: String,
        color: Long,
        playMode: PlayMode,
        numberOfRandomTasks: Int,
        sessionId: Long
    ) {
        taskGroupDataSource.insert(
            title = title,
            color = color,
            playMode = playMode.toString(),
            numberOfRandomTasks = numberOfRandomTasks.toLong(),
            sessionId = sessionId
        )
    }

    override suspend fun getTaskGroupById(taskGroupId: Long) = taskGroupDataSource
        .getDenormalizedTaskGroup(taskGroupId)
        .distinctUntilChanged()
        .mapNotNull { it.toDomainTaskGroup() }

    override suspend fun getTaskGroupBySessionId(sessionId: Long) = taskGroupDataSource
        .getBySessionId(sessionId)
        .distinctUntilChanged()
        .map { it.toDomainTaskGroup() }

    override suspend fun updateTaskGroup(
        taskGroupId: Long,
        title: String,
        color: Int,
        playMode: PlayMode,
        numberOfRandomTasks: Int,
        sortOrder: Int
    ) = taskGroupDataSource
        .update(
            taskGroupId = taskGroupId,
            title = title,
            color = color.toLong(),
            playMode = playMode.toString(),
            numberOfRandomTasks = numberOfRandomTasks.toLong(),
            sortOrder = sortOrder.toLong()
        )

    override suspend fun setTaskGroupSortOrders(taskGroupIds: List<Long>) = taskGroupDataSource
        .setTaskGroupSortOrders(taskGroupIds)

    override suspend fun deleteTaskGroupById(taskGroupId: Long) = taskGroupDataSource
        .deleteById(taskGroupId)

    override fun getLastInsertId() = taskGroupDataSource
        .getLastInsertId()
}

private fun List<DatabaseTaskGroup>.toDomainTaskGroup(): List<DomainTaskGroup> {
    return map { databaseTaskGroup ->
        databaseTaskGroup.toDomainTaskGroup()
    }
}

internal fun DatabaseTaskGroup.toDomainTaskGroup(): DomainTaskGroup {
    val title = this.title
    val color = this.color
    val playMode = PlayMode.valueOf(this.playMode)
    val numberOfRandomTasks = this.numberOfRandomTasks.toInt()
    val sortOrder = this.sortOrder.toInt()

    return DomainTaskGroup(
        id = this.id,
        title = title,
        color = color,
        playMode = playMode,
        tasks = emptyList(),
        numberOfRandomTasks = numberOfRandomTasks,
        sortOrder = sortOrder,
        sessionId = this.sessionId
    )
}

internal fun List<DenormalizedTaskGroupView>.toDomainTaskGroup(): DomainTaskGroup? {
    val firstTaskGroup = this.firstOrNull() ?: return null

    val id = firstTaskGroup.taskGroupId
    val title = firstTaskGroup.taskGroupTitle
    val color = firstTaskGroup.taskGroupColor
    val playMode = PlayMode.valueOf(firstTaskGroup.taskGroupPlayMode)
    val numberOfRandomTasks = firstTaskGroup.taskGroupNumberOfRandomTasks.toInt()
    val sortOrder = firstTaskGroup.taskGroupSortOrder.toInt()
    val sessionId = firstTaskGroup.sessionId

    val tasks = this.map {
        checkNotNull(it.taskId)
        checkNotNull(it.taskTitle)
        checkNotNull(it.taskDuration)
        checkNotNull(it.taskSortOrder)

        DomainTask(
            id = it.taskId,
            title = it.taskTitle,
            duration = it.taskDuration.seconds,
            sortOrder = it.taskSortOrder.toInt(),
            taskGroupId = id
        )
    }

    return DomainTaskGroup(
        id = id,
        title = title,
        color = color,
        playMode = playMode,
        tasks = tasks,
        numberOfRandomTasks = numberOfRandomTasks,
        sortOrder = sortOrder,
        sessionId = sessionId
    )
}
