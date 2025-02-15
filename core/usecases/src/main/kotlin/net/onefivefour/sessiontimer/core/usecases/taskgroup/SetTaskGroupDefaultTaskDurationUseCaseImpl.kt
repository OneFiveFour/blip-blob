package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupDefaultTaskDurationUseCase

@ViewModelScoped
class SetTaskGroupDefaultTaskDurationUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupDefaultTaskDurationUseCase {

    override suspend fun invoke(taskGroupId: Long, newDefaultTaskDuration: Duration) {
        taskGroupRepository.setTaskGroupDefaultTaskDuration(taskGroupId, newDefaultTaskDuration)
    }
}
