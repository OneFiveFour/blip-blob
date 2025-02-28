package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.coVerify
import io.mockk.spyk
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

    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)

    private val taskQueries = spyk(TaskQueries(driver))

    private fun sut() = TaskDataSourceImpl(
        taskQueries,
        standardTestDispatcherRule.testDispatcher
    )

    private fun useJvmDatabaseDriver() {
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
            // WHEN
            val taskId = 123L
            sut().deleteById(taskId)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteById(taskId) }
        }

    @Test
    fun `GIVEN a list of taskIds WHEN deleteByIds is called THEN the call is delegated to taskQueries`() =
        runTest {
            // WHEN
            val taskIds = listOf(123L)
            sut().deleteByIds(taskIds)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteByIds(taskIds) }
        }

    @Test
    fun `GIVEN a taskId WHEN deleteByTaskGroup is called THEN the call is delegated to taskQueries`() =
        runTest {
            // WHEN
            val taskId = 123L
            sut().deleteByTaskGroupId(taskId)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteByTaskGroupId(taskId) }
        }
}
