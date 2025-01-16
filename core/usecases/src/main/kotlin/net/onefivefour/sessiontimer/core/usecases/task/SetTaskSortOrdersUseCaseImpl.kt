package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskSortOrdersUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupSortOrdersUseCase
import javax.inject.Inject

@ViewModelScoped
class SetTaskSortOrdersUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : SetTaskSortOrdersUseCase {

    override suspend fun execute(taskIds: List<Long>) {
        taskRepository.setTaskSortOrders(taskIds)
    }
}
