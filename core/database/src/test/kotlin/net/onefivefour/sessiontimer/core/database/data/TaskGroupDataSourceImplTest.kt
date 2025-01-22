package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.minutes

internal class TaskGroupDataSourceImplTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val taskGroupQueries: TaskGroupQueries = mockk()

    private fun sut() = TaskGroupDataSourceImpl(
        taskGroupQueries,
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
    fun `GIVEN taskGroup data WHEN insert is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery {
                taskGroupQueries.new(
                    id = any(),
                    title = any(),
                    color = any(),
                    playMode = any(),
                    numberOfRandomTasks = any(),
                    defaultTaskDuration = any(),
                    sortOrder = any(),
                    sessionId = any()
                )
            } returns mockk()
            val sessionId = 321L
            val title = "Test TaskGroup Title"
            val color = 123L
            val playMode = PlayMode.N_TASKS_SHUFFLED.toString()
            val numberOfRandomTasks = 53L
            val defaultTaskDuration = 1.minutes.inWholeSeconds
            val sortOrder = 1L

            // WHEN
            sut().insert(
                title = title,
                color = color,
                playMode = playMode,
                numberOfRandomTasks = numberOfRandomTasks,
                defaultTaskDuration = defaultTaskDuration,
                sessionId = sessionId
            )

            // THEN
            coVerify(exactly = 1) {
                taskGroupQueries.new(
                    id = null,
                    title = title,
                    color = color,
                    playMode = playMode,
                    numberOfRandomTasks = numberOfRandomTasks,
                    defaultTaskDuration = defaultTaskDuration,
                    sortOrder = sortOrder,
                    sessionId = sessionId
                )
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN getById is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.denormalizedTaskGroupView(any()).executeAsOneOrNull() } returns null
            val sessionId = 123L

            // WHEN
            sut().getDenormalizedTaskGroup(sessionId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.denormalizedTaskGroupView(sessionId) }
        }

    @Test
    fun `GIVEN a sessionId WHEN getBySessionId is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.getBySessionId(any()).executeAsOneOrNull() } returns null
            val sessionId = 123L

            // WHEN
            sut().getBySessionId(sessionId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.getBySessionId(sessionId) }
        }

    @Test
    fun `GIVEN taskGroup data WHEN update is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.update(any(), any(), any(), any(), any(), any(), any()) } returns mockk()
            val taskGroupId = 5L
            val title = "Test TaskGroup Title"
            val color = 123L
            val playMode = PlayMode.N_TASKS_SHUFFLED.toString()
            val numberOfRandomTasks = 53L
            val defaultTaskDuration = 1.minutes.inWholeSeconds
            val sortOrder = 1L

            // WHEN
            sut().update(
                taskGroupId = taskGroupId,
                title = title,
                color = color,
                playMode = playMode,
                numberOfRandomTasks = numberOfRandomTasks,
                defaultTaskDuration = defaultTaskDuration,
                sortOrder = sortOrder
            )

            // THEN
            coVerify(exactly = 1) {
                taskGroupQueries.update(
                    title = title,
                    color = color,
                    playMode = playMode,
                    numberOfRandomTasks = numberOfRandomTasks,
                    defaultTaskDuration = defaultTaskDuration,
                    sortOrder = taskGroupId,
                    id = sortOrder
                )
            }
        }

    @Test
    fun `GIVEN a taskGroupId WHEN deleteById is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.deleteById(any()) } returns mockk()
            val taskGroupId = 123L

            // WHEN
            sut().deleteById(taskGroupId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.deleteById(taskGroupId) }
        }

    @Test
    fun `GIVEN a sessionId WHEN deleteBySessionId is called the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.deleteBySessionId(any()) } returns mockk()

            // WHEN
            val sessionId = 123L
            sut().deleteBySessionId(sessionId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.deleteBySessionId(sessionId) }
        }
}
