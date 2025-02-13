package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.extensions.toSixDigitsString
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import kotlin.time.Duration.Companion.minutes


internal val task1 = UiTask(
    id = 1L,
    title = "Task 1"
)
internal val task2 = UiTask(
    id = 2L,
    title = "Task 2"
)
internal val task3 = UiTask(
    id = 3L,
    title = "Task 3"
)

internal val fakeTasks = listOf(
    task1,
    task2,
    task3,
)

@Composable
internal fun uiTaskGroup() = UiTaskGroup(
    id = 1L,
    title = "TaskGroup Title",
    color = MaterialTheme.taskGroupColors.color01.first,
    onColor = MaterialTheme.taskGroupColors.color01.second,
    playMode = PlayMode.SEQUENCE,
    numberOfRandomTasks = 1,
    defaultTaskDuration = 1.minutes.toSixDigitsString(),
    sortOrder = 1,
    tasks = fakeTasks
)