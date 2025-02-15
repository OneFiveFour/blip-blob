package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.NewTaskUseCase

@ViewModelScoped
class NewTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val defaultValues: DatabaseDefaultValues
) : NewTaskUseCase {

    override suspend fun execute(taskGroupId: Long) {
        val taskGroup = taskGroupRepository.getTaskGroupById(taskGroupId).first()
        val isShuffleAll = taskGroup.tasks.size == taskGroup.numberOfRandomTasks

        val title = defaultValues.getTaskTitle()
        val duration = taskGroup.defaultTaskDuration
        taskRepository.newTask(title, duration, taskGroupId)

        if (isShuffleAll) {
            taskGroupRepository.increaseNumberOfRandomTasks(taskGroupId)
        }
    }
}
