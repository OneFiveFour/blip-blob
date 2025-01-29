package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

interface SetTaskGroupColorUseCase {
    suspend operator fun invoke(taskGroupId: Long, newColor: Int)
}