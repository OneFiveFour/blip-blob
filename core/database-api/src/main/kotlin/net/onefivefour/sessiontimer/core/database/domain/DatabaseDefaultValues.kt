package net.onefivefour.sessiontimer.core.database.domain

import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode

interface DatabaseDefaultValues {

    fun getSessionTitle(): String

    fun getTaskGroupTitle(): String

    fun getTaskGroupColors(): Pair<Long, Long>

    fun getTaskGroupPlayMode(): PlayMode

    fun getTaskGroupNumberOfRandomTasks(): Int

    fun getTaskGroupDefaultTaskDuration(): Duration

    fun getTaskTitle(): String

    fun getTaskDuration(): Duration
}
