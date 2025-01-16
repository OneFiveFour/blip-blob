package net.onefivefour.sessiontimer.core.common.domain.model

import kotlin.time.Duration.Companion.seconds

val FAKE_TASKS = listOf(
    Task(
        id = 3L,
        title = "Test Task 3L",
        duration = 10.seconds,
        sortOrder = 1,
        taskGroupId = 2L
    ),
    Task(
        id = 4L,
        title = "Test Task 4L",
        duration = 20.seconds,
        sortOrder = 2,
        taskGroupId = 2L
    ),
    Task(
        id = 5L,
        title = "Test Task 5L",
        duration = 30.seconds,
        sortOrder = 3,
        taskGroupId = 2L
    )
)