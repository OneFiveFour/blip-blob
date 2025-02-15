package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskDurationUseCase

@ViewModelScoped
class SetTaskDurationUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : SetTaskDurationUseCase {

    override suspend fun execute(taskId: Long, newDuration: Duration) {
        taskRepository.setTaskDuration(taskId, newDuration)
    }
}
