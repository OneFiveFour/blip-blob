package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.database.test.FAKE_DB_DEFAULT_VALUES
import org.junit.Test

internal class NewTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk()

    private val taskGroupRepository = mockk<TaskGroupRepository>(relaxed = true).apply {
        coEvery { getTaskGroupById(any()) } returns flowOf(
            TaskGroup(
                id = 1L,
                title = FAKE_DB_DEFAULT_VALUES.getTaskTitle(),
                color = 0xFFFFFFFF,
                onColor = 0xFF000000,
                playMode = PlayMode.N_TASKS_SHUFFLED,
                tasks = emptyList(),
                numberOfRandomTasks = 0,
                defaultTaskDuration = FAKE_DB_DEFAULT_VALUES.getTaskDuration(),
                sortOrder = 1,
                sessionId = 1L
            )
        )
    }

    // TODO make repos un-relaxed and check for all code paths in sut.

    private fun sut() = NewTaskUseCaseImpl(
        taskRepository,
        taskGroupRepository,
        FAKE_DB_DEFAULT_VALUES
    )

    @Test
    fun `GIVEN a taskGroupId WHEN executing the UseCase THEN the taskRepository creates a new task`() =
        runTest {
            // GIVEN
            coEvery { taskRepository.newTask(any(), any(), any()) } returns Unit
            val taskGroupId = 1L

            // WHEN
            sut().execute(taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskRepository.newTask(
                    FAKE_DB_DEFAULT_VALUES.getTaskTitle(),
                    FAKE_DB_DEFAULT_VALUES.getTaskDuration(),
                    taskGroupId
                )
            }
        }
}
