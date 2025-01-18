package net.onefivefour.sessiontimer.feature.sessionoverview

import kotlinx.datetime.Instant
import net.onefivefour.sessiontimer.core.common.domain.model.Session

data class UiSession(
    val id: Long,
    val title: String,
    val sortOrder: Int,
    val createdAt: Instant
)

fun List<Session>.toUiSessions() = map {
    it.toUiSession()
}

fun Session.toUiSession() = UiSession(
    id = this.id,
    title = this.title,
    sortOrder = this.sortOrder,
    createdAt = this.createdAt
)
