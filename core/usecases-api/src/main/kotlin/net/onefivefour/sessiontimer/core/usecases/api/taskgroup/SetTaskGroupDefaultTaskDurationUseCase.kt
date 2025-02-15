package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

import kotlin.time.Duration

interface SetTaskGroupDefaultTaskDurationUseCase {
    suspend operator fun invoke(taskGroupId: Long, newDefaultTaskDuration: Duration)
}
