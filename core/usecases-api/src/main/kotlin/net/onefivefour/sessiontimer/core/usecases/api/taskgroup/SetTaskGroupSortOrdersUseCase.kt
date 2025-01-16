package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

interface SetTaskGroupSortOrdersUseCase {
    suspend fun execute(taskGroupIds: List<Long>)
}
