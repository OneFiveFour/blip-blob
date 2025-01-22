package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import org.junit.Test
import kotlin.time.Duration.Companion.minutes

internal class UpdateTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk(relaxed = true)

    private fun sut() = UpdateTaskGroupUseCaseImpl(
        taskGroupRepository
    )

    @Test
    fun `GIVEN taskGroup data WHEN executing the UseCase THEN it updates the taskGroup`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            val title = "Task Group Title"
            val color = 0xF0F0F0
            val playMode = PlayMode.N_TASKS_SHUFFLED
            val numberOfRandomTasks = 19
            val defaultTaskDuration = 1.minutes
            val sortOrder = 1

            // WHEN
            sut().execute(
                id = taskGroupId,
                title = title,
                color = color,
                playMode = playMode,
                numberOfRandomTasks = numberOfRandomTasks,
                defaultTaskDuration = defaultTaskDuration,
                sortOrder = sortOrder
            )

            // THEN
            coVerify(exactly = 1) {
                taskGroupRepository.updateTaskGroup(
                    taskGroupId = taskGroupId,
                    title = title,
                    color = color,
                    playMode = playMode,
                    numberOfRandomTasks = numberOfRandomTasks,
                    defaultTaskDuration = defaultTaskDuration,
                    sortOrder = sortOrder
                )
            }
        }
}
