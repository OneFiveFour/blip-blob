package net.onefivefour.sessiontimer.core.usecases.api.task

interface SetTaskTitleUseCase {
    suspend fun execute(taskId: Long, title: String)
}
