package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
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
            val durationInSeconds = 300
            val taskGroupId = 1L

            // WHEN
            sut().newTask(title, durationInSeconds, taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskDataSource.insert(
                    title = title,
                    durationInSeconds = durationInSeconds.toLong(),
                    taskGroupId = taskGroupId
                )
            }
        }

    @Test
    fun `GIVEN task data WHEN updateTask is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            coEvery { taskDataSource.update(any(), any(), any(), any()) } returns Unit
            val taskId = 1L
            val title = "Updated Task"
            val duration = 5.minutes
            val sortOrder = 1

            // WHEN
            sut().updateTask(
                taskId = taskId,
                title = title,
                duration = duration,
                sortOrder = sortOrder
            )

            // THEN
            coVerify(exactly = 1) {
                taskDataSource.update(
                    taskId = taskId,
                    title = title,
                    durationInSeconds = duration.toLong(DurationUnit.SECONDS),
                    sortOrder = sortOrder.toLong()
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
