package net.onefivefour.sessiontimer.core.common.domain.model

import kotlin.time.Duration
import kotlinx.datetime.Instant

data class Session(
    val id: Long,
    val title: String,
    val sortOrder: Int,
    val taskGroups: List<TaskGroup>,
    val createdAt: Instant
)

fun Session.getTotalDuration(): Duration {
    return this.taskGroups
        .flatMap { it.tasks }
        .map { it.duration }
        .fold(Duration.ZERO) { acc, duration -> acc + duration }
}
