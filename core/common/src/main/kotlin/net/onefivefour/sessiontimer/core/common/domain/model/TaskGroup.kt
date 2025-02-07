package net.onefivefour.sessiontimer.core.common.domain.model

import kotlin.time.Duration

data class TaskGroup(
    val id: Long,
    val title: String,
    val color: Long,
    val onColor: Long,
    val playMode: PlayMode,
    val tasks: List<Task>,
    val numberOfRandomTasks: Int = 0,
    val defaultTaskDuration: Duration,
    val sortOrder: Int,
    val sessionId: Long
)
