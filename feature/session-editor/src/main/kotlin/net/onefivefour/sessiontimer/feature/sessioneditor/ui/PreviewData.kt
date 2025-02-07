package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import kotlin.time.Duration.Companion.minutes

private val now = Clock.System.now()

internal val uiTask1 = UiTask(
    id = 1L,
    title = "Test Task 1L",
    duration = 10.seconds,
    sortOrder = 1,
    createdAt = now.plus(1.seconds)
)

internal val uiTask2 = UiTask(
    id = 2L,
    title = "Test Task 2L",
    duration = 20.seconds,
    sortOrder = 2,
    createdAt = now.plus(2.seconds)
)

internal val uiTask3 = UiTask(
    id = 3L,
    title = "Test Task 3L",
    duration = 90.seconds,
    sortOrder = 3,
    createdAt = now.plus(3.seconds)
)

internal val fakeUiTasks = listOf(
    uiTask1,
    uiTask2,
    uiTask3
)

@Composable
internal fun fakeUiTaskGroup() = UiTaskGroup(
    id = 1L,
    title = "TaskGroup Title",
    color = MaterialTheme.taskGroupColors.color06.first,
    onColor = MaterialTheme.taskGroupColors.color06.second,
    playMode = PlayMode.SEQUENCE,
    numberOfRandomTasks = 0,
    defaultTaskDuration = 1.minutes,
    tasks = fakeUiTasks
)
