package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test

internal class DeleteTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private val taskGroupRepository = mockk<TaskGroupRepository>(relaxed = true).apply {
        coEvery { getTaskGroupById(any()) } returns flowOf(
            TaskGroup(
                id = 1L,
                title = "TaskGroup Title",
                color = 0xFFFFFFFF,
                onColor = 0xFF000000,
                playMode = PlayMode.N_TASKS_SHUFFLED,
                tasks = emptyList(),
                numberOfRandomTasks = 0,
                defaultTaskDuration = 1.seconds,
                sortOrder = 1,
                sessionId = 1L
            )
        )
    }

    // TODO make repos un-relaxed and check for all code paths in sut.

    private fun sut() = DeleteTaskUseCaseImpl(
        taskRepository,
        taskGroupRepository
    )

    @Test
    fun `GIVEN a taskId WHEN executing the UseCase THEN the taskRepository deletes the task`() =
        runTest {
            // GIVEN
            coEvery { taskRepository.deleteTask(any()) } returns Unit
            val taskId = 1L
            val taskGroupId = 2L

            // WHEN
            sut().execute(taskId, taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskRepository.deleteTask(taskId)
            }
        }
}
