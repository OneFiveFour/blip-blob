package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskTitleUseCase

@ViewModelScoped
class SetTaskTitleUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : SetTaskTitleUseCase {

    override suspend fun execute(taskId: Long, title: String) {
        taskRepository.setTaskTitle(taskId, title)
    }
}
