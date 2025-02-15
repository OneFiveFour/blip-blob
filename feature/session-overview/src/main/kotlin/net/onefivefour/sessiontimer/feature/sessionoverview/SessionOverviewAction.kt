package net.onefivefour.sessiontimer.feature.sessionoverview

internal sealed class SessionOverviewAction {
    data object CreateSession : SessionOverviewAction()
    data class UpdateSessionSortOrders(val sessionIds: List<Long>) : SessionOverviewAction()
    data class DeleteSession(val sessionId: Long) : SessionOverviewAction()
}
