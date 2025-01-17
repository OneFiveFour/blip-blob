package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupSortOrdersUseCase

@ViewModelScoped
class SetTaskGroupSortOrdersUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupSortOrdersUseCase {

    override suspend fun execute(taskGroupIds: List<Long>) {
        taskGroupRepository.setTaskGroupSortOrders(taskGroupIds)
    }
}
