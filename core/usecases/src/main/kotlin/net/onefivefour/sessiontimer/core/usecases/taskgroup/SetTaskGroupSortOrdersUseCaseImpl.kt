package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionSortOrdersUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupSortOrdersUseCase
import javax.inject.Inject

@ViewModelScoped
class SetTaskGroupSortOrdersUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupSortOrdersUseCase {

    override suspend fun execute(taskGroupIds: List<Long>) {
        taskGroupRepository.setTaskGroupSortOrders(taskGroupIds)
    }
}
