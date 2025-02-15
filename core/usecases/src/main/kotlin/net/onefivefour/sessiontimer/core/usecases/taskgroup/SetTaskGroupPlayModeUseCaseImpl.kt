package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupPlayModeUseCase

@ViewModelScoped
class SetTaskGroupPlayModeUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupPlayModeUseCase {

    override suspend fun invoke(
        taskGroupId: Long,
        newPlayMode: PlayMode,
        newNumberOfRandomTasks: Int
    ) {
        taskGroupRepository.setTaskGroupPlayMode(taskGroupId, newPlayMode, newNumberOfRandomTasks)
    }
}
