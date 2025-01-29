package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

interface SetTaskGroupTitleUseCase {
    suspend operator fun invoke(taskGroupId: Long, newTitle: String)
}