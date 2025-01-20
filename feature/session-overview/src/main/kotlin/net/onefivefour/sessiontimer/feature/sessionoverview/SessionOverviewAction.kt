package net.onefivefour.sessiontimer.feature.sessionoverview

sealed class SessionOverviewAction {
    data object CreateNewSession : SessionOverviewAction()
    data class UpdateSessionSortOrders(val sessionIds: List<Long>) : SessionOverviewAction()
    data class DeleteSession(val sessionId: Long) : SessionOverviewAction()
}