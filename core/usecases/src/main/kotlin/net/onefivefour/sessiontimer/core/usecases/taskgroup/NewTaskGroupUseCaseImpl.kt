package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.NewTaskGroupUseCase

@ViewModelScoped
class NewTaskGroupUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
    private val defaultValues: DatabaseDefaultValues
) : NewTaskGroupUseCase {

    override suspend fun execute(sessionId: Long) : Long {
        val taskGroupTitle = defaultValues.getTaskGroupTitle()
        val taskGroupColors = defaultValues.getTaskGroupColors()
        val taskGroupColor = taskGroupColors.first
        val taskGroupOnColor = taskGroupColors.second
        val taskGroupPlayMode = defaultValues.getTaskGroupPlayMode()
        val taskGroupNumberOfRandomTasks = defaultValues.getTaskGroupNumberOfRandomTasks()
        val taskGroupDefaultTaskDuration = defaultValues.getTaskGroupDefaultTaskDuration()

        return taskGroupRepository.newTaskGroup(
            title = taskGroupTitle,
            color = taskGroupColor,
            onColor = taskGroupOnColor,
            playMode = taskGroupPlayMode,
            numberOfRandomTasks = taskGroupNumberOfRandomTasks,
            defaultTaskDuration = taskGroupDefaultTaskDuration,
            sessionId = sessionId
        )
    }
}
