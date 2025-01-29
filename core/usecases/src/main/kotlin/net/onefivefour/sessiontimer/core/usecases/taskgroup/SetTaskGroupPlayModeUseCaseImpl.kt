package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupColorUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupPlayModeUseCase
import javax.inject.Inject

@ViewModelScoped
class SetTaskGroupPlayModeUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupPlayModeUseCase {

    override suspend fun invoke(taskGroupId: Long, newPlayMode: PlayMode, newNumberOfRandomTasks: Int) {
        taskGroupRepository.setTaskGroupPlayMode(taskGroupId, newPlayMode, newNumberOfRandomTasks)
    }

}
