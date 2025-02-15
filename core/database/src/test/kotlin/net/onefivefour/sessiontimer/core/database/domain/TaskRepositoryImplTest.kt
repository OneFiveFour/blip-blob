package net.onefivefour.sessiontimer.core.database.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import org.junit.Test

internal class TaskRepositoryImplTest {

    private val taskDataSource: TaskDataSource = mockk()

    private fun sut() = TaskRepositoryImpl(
        taskDataSource
    )

    @Test
    fun `GIVEN task data WHEN newTask is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            coEvery { taskDataSource.insert(any(), any(), any()) } returns Unit
            val title = "Sample Task"
            val duration = 300.seconds
            val taskGroupId = 1L

            // WHEN
            sut().newTask(title, duration, taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskDataSource.insert(
                    title = title,
                    durationInSeconds = duration.inWholeSeconds,
                    taskGroupId = taskGroupId
                )
            }
        }

    @Test
    fun `GIVEN task data WHEN updateTask is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            coEvery { taskDataSource.setTaskTitle(any(), any()) } returns Unit
            val taskId = 1L
            val title = "Updated Task"

            // WHEN
            sut().setTaskTitle(
                taskId = taskId,
                title = title
            )

            // THEN
            coVerify(exactly = 1) {
                taskDataSource.setTaskTitle(
                    taskId = taskId,
                    title = title
                )
            }
        }

    @Test
    fun `GIVEN a taskId WHEN deleteTaskById is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            val taskId = 1L
            coEvery { taskDataSource.deleteById(any()) } returns Unit

            // WHEN
            sut().deleteTask(taskId)

            // THEN
            coVerify(exactly = 1) { taskDataSource.deleteById(taskId) }
        }

    @Test
    fun `GIVEN a taskGroupId WHEN deleteTaskByTaskGroupId is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            coEvery { taskDataSource.deleteByTaskGroupId(any()) } returns Unit

            // WHEN
            sut().deleteTasksByTaskGroupId(taskGroupId)

            // THEN
            coVerify(exactly = 1) { taskDataSource.deleteByTaskGroupId(taskGroupId) }
        }
}
