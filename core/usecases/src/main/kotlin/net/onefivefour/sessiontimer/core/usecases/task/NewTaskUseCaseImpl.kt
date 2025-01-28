package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.NewTaskUseCase
import javax.inject.Inject

@ViewModelScoped
class NewTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val defaultValues: DatabaseDefaultValues,
) : NewTaskUseCase {

    override suspend fun execute(taskGroupId: Long) {
        val title = defaultValues.getTaskTitle()
        val duration = defaultValues.getTaskDuration()
        taskRepository.newTask(title, duration, taskGroupId)
        taskGroupRepository.increaseNumberOfRandomTasks(taskGroupId)
    }
}
