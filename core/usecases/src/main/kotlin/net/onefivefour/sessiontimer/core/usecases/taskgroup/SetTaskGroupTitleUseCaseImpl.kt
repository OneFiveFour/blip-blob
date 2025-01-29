package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupTitleUseCase
import javax.inject.Inject

@ViewModelScoped
class SetTaskGroupTitleUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupTitleUseCase {

    override suspend fun invoke(taskGroupId: Long, newTitle: String) {
        taskGroupRepository.setTaskGroupTitle(taskGroupId, newTitle)
    }

}
