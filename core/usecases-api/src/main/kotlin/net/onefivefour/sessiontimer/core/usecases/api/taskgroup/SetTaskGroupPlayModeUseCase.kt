package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode

interface SetTaskGroupPlayModeUseCase {
    suspend operator fun invoke(
        taskGroupId: Long,
        newPlayMode: PlayMode,
        newNumberOfRandomTasks: Int
    )
}
