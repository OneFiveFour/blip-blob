package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import kotlin.time.Duration.Companion.seconds


internal val task1 = UiTask(
    id = 1L,
    title = "Test Task 1L",
    duration = 10.seconds,
    sortOrder = 1
)

internal val task2 = UiTask(
    id = 2L,
    title = "Test Task 2L",
    duration = 20.seconds,
    sortOrder = 2
)

internal val task3 = UiTask(
    id = 3L,
    title = "Test Task 3L",
    duration = 30.seconds,
    sortOrder = 3
)

internal val fakeTasks = listOf(
    task1,
    task2,
    task3,
)