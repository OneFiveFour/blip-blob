package net.onefivefour.sessiontimer.core.common.domain.model

import kotlin.time.Duration
import kotlinx.datetime.Instant

data class Task(
    val id: Long,
    val title: String,
    val duration: Duration,
    val sortOrder: Int,
    val taskGroupId: Long,
    val createdAt: Instant
)
