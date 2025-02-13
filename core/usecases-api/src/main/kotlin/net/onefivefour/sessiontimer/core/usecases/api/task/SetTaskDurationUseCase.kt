package net.onefivefour.sessiontimer.core.usecases.api.task

import kotlin.time.Duration

interface SetTaskDurationUseCase {
    suspend fun execute(taskId: Long, newDuration: Duration)
}
