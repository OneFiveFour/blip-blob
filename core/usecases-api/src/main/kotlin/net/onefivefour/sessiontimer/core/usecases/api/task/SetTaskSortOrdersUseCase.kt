package net.onefivefour.sessiontimer.core.usecases.api.task

interface SetTaskSortOrdersUseCase {
    suspend fun execute(taskIds: List<Long>)
}
