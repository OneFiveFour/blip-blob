package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup

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
    task3
)

@Composable
internal fun fakeUiTaskGroup() = UiTaskGroup(
    id = 1L,
    title = "TaskGroup Title",
    color = MaterialTheme.taskGroupColors.color03,
    playMode = PlayMode.SEQUENCE,
    numberOfRandomTasks = 0,
    tasks = fakeTasks
)
