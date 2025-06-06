package net.onefivefour.sessiontimer.core.database.test

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues

val FAKE_DB_DEFAULT_VALUES = object : DatabaseDefaultValues {

    override fun getSessionTitle(): String {
        return "DEFAULT_SESSION_TITLE"
    }

    override fun getTaskGroupTitle(): String {
        return "DEFAULT_SESSION_TITLE"
    }

    override fun getTaskGroupColors(): Pair<Long, Long> {
        return 0xFF0000L to 0xFFFF00L
    }

    override fun getTaskGroupPlayMode(): PlayMode {
        return PlayMode.SEQUENCE
    }

    override fun getTaskGroupNumberOfRandomTasks(): Int {
        return 15
    }

    override fun getTaskGroupDefaultTaskDuration(): Duration {
        return 5.minutes
    }

    override fun getTaskTitle(): String {
        return "DEFAULT_TASK_TITLE"
    }

    override fun getTaskDuration(): Duration {
        return 3.seconds
    }
}
