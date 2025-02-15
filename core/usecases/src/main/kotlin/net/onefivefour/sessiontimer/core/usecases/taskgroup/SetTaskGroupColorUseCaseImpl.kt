package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupColorUseCase

@ViewModelScoped
class SetTaskGroupColorUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupColorUseCase {

    override suspend fun invoke(taskGroupId: Long, newColor: Int, newOnColor: Int) {
        taskGroupRepository.setTaskGroupColor(taskGroupId, newColor, newOnColor)
    }
}
