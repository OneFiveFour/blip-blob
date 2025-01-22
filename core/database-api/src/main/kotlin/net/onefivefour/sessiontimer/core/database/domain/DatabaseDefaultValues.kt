package net.onefivefour.sessiontimer.core.database.domain

import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import kotlin.time.Duration

interface DatabaseDefaultValues {

    fun getSessionTitle(): String

    fun getTaskGroupTitle(): String
    fun getTaskGroupColor(): Long
    fun getTaskGroupPlayMode(): PlayMode
    fun getTaskGroupNumberOfRandomTasks(): Int
    fun getTaskGroupDefaultTaskDuration(): Duration

    fun getTaskTitle(): String
    fun getTaskDuration(): Duration
}
