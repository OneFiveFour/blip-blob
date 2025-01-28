package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.first
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.DeleteTaskUseCase

@ViewModelScoped
class DeleteTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskGroupRepository: TaskGroupRepository
) : DeleteTaskUseCase {

    override suspend fun execute(taskId: Long, taskGroupId: Long) {
        val taskGroup = taskGroupRepository.getTaskGroupById(taskGroupId).first()
        val isShuffleAll = taskGroup.tasks.size == taskGroup.numberOfRandomTasks

        taskRepository.deleteTask(taskId)

        if (isShuffleAll) {
            taskGroupRepository.decreaseNumberOfRandomTasks(taskGroupId)
        }
    }
}
