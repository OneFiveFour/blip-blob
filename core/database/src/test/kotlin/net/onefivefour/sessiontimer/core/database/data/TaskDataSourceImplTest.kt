package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.TaskQueries
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class TaskDataSourceImplTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val taskQueries: TaskQueries = mockk()

    private fun sut() = TaskDataSourceImpl(
        taskQueries,
        standardTestDispatcherRule.testDispatcher
    )

    private fun useJvmDatabaseDriver() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
    }

    @Before
    fun setup() {
        useJvmDatabaseDriver()
    }

    @Test
    fun `GIVEN data for a task WHEN insert is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.new(any(), any(), any(), any(), any(), any()) } returns mockk()
            val taskGroupId = 321L
            val duration = 123L
            val taskTitle = "Test Task Title"
            val sortOrder = 1L

            // WHEN
            sut().insert(taskTitle, duration, taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskQueries.new(
                    id = null,
                    title = taskTitle,
                    durationInSeconds = duration,
                    sortOrder = sortOrder,
                    taskGroupId = taskGroupId,
                    createdAt = any()
                )
            }
        }

    @Test
    fun `GIVEN task data WHEN setTaskTitle is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.setTaskTitle(any(), any()) } returns mockk()
            val taskId = 123L
            val title = "Test Title"

            // WHEN
            sut().setTaskTitle(taskId, title)

            // THEN
            coVerify(exactly = 1) { taskQueries.setTaskTitle(title, taskId) }
        }

    @Test
    fun `GIVEN a taskId WHEN deleteById is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.deleteById(any()) } returns mockk()

            // WHEN
            val taskId = 123L
            sut().deleteById(taskId)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteById(taskId) }
        }

    @Test
    fun `GIVEN a list of taskIds WHEN deleteByIds is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.deleteByIds(any()) } returns mockk()

            // WHEN
            val taskIds = listOf(123L)
            sut().deleteByIds(taskIds)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteByIds(taskIds) }
        }

    @Test
    fun `GIVEN a taskId WHEN deleteByTaskGroup is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.deleteByTaskGroupId(any()) } returns mockk()

            // WHEN
            val taskId = 123L
            sut().deleteByTaskGroupId(taskId)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteByTaskGroupId(taskId) }
        }
}
