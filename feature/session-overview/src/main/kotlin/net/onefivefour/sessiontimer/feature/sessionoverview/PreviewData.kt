package net.onefivefour.sessiontimer.feature.sessionoverview

import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.minutes


private val uiSession1 = UiSession(
    id = 1L,
    title = "Title Session 1",
    sortOrder = 1,
    createdAt = Clock.System.now()
)

private val uiSession2 = UiSession(
    id = 2L,
    title = "Title Session 2",
    sortOrder = 2,
    createdAt = Clock.System.now().plus(1.minutes)
)

internal val uiSessionList = listOf(
    uiSession1,
    uiSession2
)