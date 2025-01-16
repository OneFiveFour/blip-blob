package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState
import kotlin.time.Duration.Companion.seconds


internal val task1 = UiTask(
    id = 1L,
    taskTitle = "Test Task 1L",
    taskDuration = 10.seconds,
    taskGroupColor = Color(0xFFFF0000),
    taskGroupTitle = "Test Task Group"
)

internal val task2 = UiTask(
    id = 2L,
    taskTitle = "Test Task 2L",
    taskDuration = 20.seconds,
    taskGroupColor = Color(0xFF1F726B),
    taskGroupTitle = "Test Task Group"
)

internal val task3 = UiTask(
    id = 3L,
    taskTitle = "Test Task 3L",
    taskDuration = 30.seconds,
    taskGroupColor = Color(0xFFBD5D18),
    taskGroupTitle = "Test Task Group"
)

internal val fakeTasks = listOf(
    task1,
    task2,
    task3,
)

internal val uiTimerStateActive = UiTimerState.Active(
    isRunning = true,
    totalDuration = 60.seconds,
    elapsedTotalDuration = 20.seconds,
    elapsedTaskDuration = 10.seconds,
    currentTask = task2,
    tasks = fakeTasks
)