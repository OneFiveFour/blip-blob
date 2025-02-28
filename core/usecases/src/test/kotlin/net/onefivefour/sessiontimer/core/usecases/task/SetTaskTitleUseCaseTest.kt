package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test

internal class SetTaskTitleUseCaseTest {

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private fun sut() = SetTaskTitleUseCaseImpl(
        taskRepository
    )

    @Test
    fun `GIVEN task data WHEN executing the UseCase THEN the taskRepository updates the task title`() =
        runTest {
            // GIVEN
            val taskId = 1L
            val title = "New Task Title"

            // WHEN
            sut().execute(
                taskId = taskId,
                title = title
            )

            // THEN
            coVerify(exactly = 1) {
                taskRepository.setTaskTitle(
                    taskId = taskId,
                    title = title
                )
            }
        }
}
