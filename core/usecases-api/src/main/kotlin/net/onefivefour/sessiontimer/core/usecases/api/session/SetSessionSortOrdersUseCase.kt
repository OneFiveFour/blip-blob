package net.onefivefour.sessiontimer.core.usecases.api.session

interface SetSessionSortOrdersUseCase {
    suspend fun execute(sessionIds: List<Long>)
}
