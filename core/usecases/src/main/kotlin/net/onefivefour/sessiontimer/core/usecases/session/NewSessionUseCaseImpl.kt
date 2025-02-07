package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.session.NewSessionUseCase

@ViewModelScoped
class NewSessionUseCaseImpl @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
    private val defaultValues: DatabaseDefaultValues
) : NewSessionUseCase {

    override suspend fun execute() {
        val defaultSessionTitle = defaultValues.getSessionTitle()
        sessionRepository.newSession(defaultSessionTitle)

        val sessionId = sessionRepository.getLastInsertId()
        val taskGroupTitle = defaultValues.getTaskGroupTitle()
        val taskGroupColors = defaultValues.getTaskGroupColors()
        val taskGroupColor = taskGroupColors.first
        val taskGroupOnColor = taskGroupColors.second
        val taskGroupPlayMode = defaultValues.getTaskGroupPlayMode()
        val taskGroupNumberOfRandomTasks = defaultValues.getTaskGroupNumberOfRandomTasks()
        val taskGroupDefaultTaskDuration = defaultValues.getTaskGroupDefaultTaskDuration()

        taskGroupRepository.newTaskGroup(
            title = taskGroupTitle,
            color = taskGroupColor,
            onColor = taskGroupOnColor,
            playMode = taskGroupPlayMode,
            numberOfRandomTasks = taskGroupNumberOfRandomTasks,
            defaultTaskDuration = taskGroupDefaultTaskDuration,
            sessionId = sessionId
        )

        val taskGroupId = taskGroupRepository.getLastInsertId()
        val taskTitle = defaultValues.getTaskTitle()
        val taskDuration = defaultValues.getTaskDuration()
        taskRepository.newTask(taskTitle, taskDuration, taskGroupId)
    }
}
