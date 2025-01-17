package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskTitleUseCase
import javax.inject.Inject

@ViewModelScoped
class SetTaskTitleUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : SetTaskTitleUseCase {

    override suspend fun execute(taskId: Long, title: String) {
        taskRepository.setTaskTitle(taskId, title)
    }
}
