package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupColorUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupDefaultTaskDurationUseCase
import javax.inject.Inject
import kotlin.time.Duration

@ViewModelScoped
class SetTaskGroupDefaultTaskDurationUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : SetTaskGroupDefaultTaskDurationUseCase {

    override suspend fun invoke(taskGroupId: Long, newDefaultTaskDuration: Duration) {
        taskGroupRepository.setTaskGroupDefaultTaskDuration(taskGroupId, newDefaultTaskDuration)
    }

}
