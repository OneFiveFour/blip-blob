package net.onefivefour.sessiontimer.core.common.domain.model

/**
 * Contains 1 TaskGroup with 2 Tasks.
 * The TaskGroup is set to PlayMode.SEQUENCE.
 */
val FAKE_SESSION = Session(
    id = 1L,
    title = "Test Session",
    taskGroups = listOf(
        TaskGroup(
            id = 2L,
            title = "Test Task Group",
            color = 0xFF0000,
            playMode = PlayMode.SEQUENCE,
            tasks = FAKE_TASKS,
            sortOrder = 1,
            sessionId = 1L
        )
    ),
    sortOrder = 2
)
