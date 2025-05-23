package net.onefivefour.sessiontimer.core.common.domain.model

import kotlin.time.Duration.Companion.minutes
import kotlinx.datetime.Clock

/**
 * Contains 1 TaskGroup with 2 Tasks.
 * The TaskGroup is set to PlayMode.SEQUENCE.
 */
val FAKE_SESSION = Session(
    id = 1L,
    title = "Test Session",
    taskGroups = listOf(
        TaskGroup(
            id = 2L,
            title = "Test Task Group",
            color = 0xFF0000,
            onColor = 0xFFFF00,
            playMode = PlayMode.SEQUENCE,
            numberOfRandomTasks = 1,
            defaultTaskDuration = 1.minutes,
            tasks = FAKE_TASKS,
            sortOrder = 1,
            sessionId = 1L
        )
    ),
    sortOrder = 2,
    createdAt = Clock.System.now()
)
