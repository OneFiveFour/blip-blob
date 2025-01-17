package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskSortOrdersUseCase

@ViewModelScoped
class SetTaskSortOrdersUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : SetTaskSortOrdersUseCase {

    override suspend fun execute(taskIds: List<Long>) {
        taskRepository.setTaskSortOrders(taskIds)
    }
}
