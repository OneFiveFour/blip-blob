package net.onefivefour.sessiontimer.core.common.domain.model

import kotlinx.datetime.Instant
import kotlin.time.Duration

data class Task(
    val id: Long,
    val title: String,
    val duration: Duration,
    val sortOrder: Int,
    val taskGroupId: Long,
    val createdAt: Instant
)
