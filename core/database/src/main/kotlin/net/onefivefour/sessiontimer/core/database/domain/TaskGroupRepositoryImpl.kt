package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.datetime.Instant
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.DenormalizedTaskGroupView
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup as DomainTaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup

internal class TaskGroupRepositoryImpl @Inject constructor(
    private val taskGroupDataSource: TaskGroupDataSource
) : TaskGroupRepository {

    override suspend fun newTaskGroup(
        title: String,
        color: Long,
        onColor: Long,
        playMode: PlayMode,
        numberOfRandomTasks: Int,
        defaultTaskDuration: Duration,
        sessionId: Long,
    ) {
        taskGroupDataSource.insert(
            title = title,
            color = color,
            onColor = onColor,
            playMode = playMode.toString(),
            numberOfRandomTasks = numberOfRandomTasks.toLong(),
            defaultTaskDuration = defaultTaskDuration.inWholeSeconds,
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

    override suspend fun increaseNumberOfRandomTasks(
        taskGroupId: Long,
    ) = taskGroupDataSource
        .increaseNumberOfRandomTasks(taskGroupId)

    override suspend fun decreaseNumberOfRandomTasks(taskGroupId: Long) = taskGroupDataSource
        .decreaseNumberOfRandomTasks(taskGroupId)

    override suspend fun setTaskGroupSortOrders(taskGroupIds: List<Long>) = taskGroupDataSource
        .setTaskGroupSortOrders(taskGroupIds)

    override suspend fun deleteTaskGroupById(taskGroupId: Long) = taskGroupDataSource
        .deleteById(taskGroupId)

    override suspend fun setTaskGroupTitle(taskGroupId: Long, newTitle: String) =
        taskGroupDataSource
            .setTaskGroupTitle(taskGroupId, newTitle)

    override suspend fun setTaskGroupPlayMode(
        taskGroupId: Long,
        newPlayMode: PlayMode,
        newNumberOfRandomTasks: Int
    ) = taskGroupDataSource
        .setTaskGroupPlayMode(taskGroupId, newPlayMode.toString(), newNumberOfRandomTasks.toLong())

    override suspend fun setTaskGroupDefaultTaskDuration(
        taskGroupId: Long,
        newDefaultTaskDuration: Duration
    ) = taskGroupDataSource
        .setTaskGroupDefaultTaskDuration(taskGroupId, newDefaultTaskDuration.inWholeSeconds)

    override suspend fun setTaskGroupColor(taskGroupId: Long, newColor: Int, newOnColor: Int) = taskGroupDataSource
        .setTaskGroupColor(taskGroupId, newColor.toLong(), newOnColor.toLong())

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
    val onColor = this.onColor
    val playMode = PlayMode.valueOf(this.playMode)
    val numberOfRandomTasks = this.numberOfRandomTasks.toInt()
    val defaultTaskDuration = this.defaultTaskDuration.seconds
    val sortOrder = this.sortOrder.toInt()

    return DomainTaskGroup(
        id = this.id,
        title = title,
        color = color,
        onColor = onColor,
        playMode = playMode,
        tasks = emptyList(),
        numberOfRandomTasks = numberOfRandomTasks,
        defaultTaskDuration = defaultTaskDuration,
        sortOrder = sortOrder,
        sessionId = this.sessionId
    )
}

internal fun List<DenormalizedTaskGroupView>.toDomainTaskGroup(): DomainTaskGroup? {
    val firstTaskGroup = this.firstOrNull() ?: return null

    val id = firstTaskGroup.taskGroupId
    val title = firstTaskGroup.taskGroupTitle
    val color = firstTaskGroup.taskGroupColor
    val onColor = firstTaskGroup.taskGroupOnColor
    val playMode = PlayMode.valueOf(firstTaskGroup.taskGroupPlayMode)
    val numberOfRandomTasks = firstTaskGroup.taskGroupNumberOfRandomTasks.toInt()
    val defaultTaskDuration = firstTaskGroup.taskGroupDefaultTaskDuration.seconds
    val sortOrder = firstTaskGroup.taskGroupSortOrder.toInt()
    val sessionId = firstTaskGroup.sessionId

    val tasks = this.mapNotNull {

        if (it.taskId == null) {
            return@mapNotNull null
        }

        checkNotNull(it.taskId)
        checkNotNull(it.taskTitle)
        checkNotNull(it.taskDuration)
        checkNotNull(it.taskSortOrder)
        checkNotNull(it.taskCreatedAt)

        DomainTask(
            id = it.taskId,
            title = it.taskTitle,
            duration = it.taskDuration.seconds,
            sortOrder = it.taskSortOrder.toInt(),
            taskGroupId = id,
            createdAt = Instant.fromEpochMilliseconds(it.taskCreatedAt)
        )
    }

    return DomainTaskGroup(
        id = id,
        title = title,
        color = color,
        onColor = onColor,
        playMode = playMode,
        tasks = tasks,
        numberOfRandomTasks = numberOfRandomTasks,
        defaultTaskDuration = defaultTaskDuration,
        sortOrder = sortOrder,
        sessionId = sessionId
    )
}
