package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.SessionQueries
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SessionDataSourceImplTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)

    private val sessionQueries = spyk(SessionQueries(driver))

    private fun sut() = SessionDataSourceImpl(
        sessionQueries,
        standardTestDispatcherRule.testDispatcher
    )

    private fun useJvmDatabaseDriver() {
        Database.Schema.create(driver)
    }

    @Before
    fun setup() {
        useJvmDatabaseDriver()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN data for new session WHEN insert is called THEN the call is delegated to sessionQueries`() =
        runTest {
            // GIVEN
            val title = "title"

            // WHEN
            sut().insert(title)

            // THEN
            verify(exactly = 1) { sessionQueries.transaction(any(), any()) }
            verify(exactly = 1) { sessionQueries.findMaxSortOrder() }
            verify(exactly = 1) { sessionQueries.new(any(), title, any(), any()) }
        }

    @Test
    fun `GIVEN mocked sessions WHEN getAll is called THEN the call is delegated to sessionQueries`() =
        runTest {
            // WHEN
            sut().getAll()

            // THEN
            coVerify(exactly = 1) { sessionQueries.getAll() }
        }

    @Test
    fun `GIVEN a sessionId WHEN getDenormalizedSessionView is called THEN the call is delegated to sessionQueries`() =
        runTest {
            val sessionId = 123L

            // WHEN
            sut().getDenormalizedSession(sessionId)

            // THEN
            coVerify(exactly = 1) { sessionQueries.denormalizedSessionView(sessionId) }
        }

    @Test
    fun `GIVEN a sessionId WHEN deleteById is called THEN the call is delegated to sessionQueries`() =
        runTest {
            val sessionId = 123L

            // WHEN
            sut().deleteById(sessionId)

            // THEN
            coVerify(exactly = 1) { sessionQueries.deleteById(sessionId) }
        }

    @Test
    fun `GIVEN a sessionId and title WHEN setTitle is called THEN the call is delegated to sessionQueries`() =
        runTest {
            val sessionId = 123L
            val title = "Test Title"

            // WHEN
            sut().setTitle(sessionId, title)

            // THEN
            coVerify(exactly = 1) { sessionQueries.setTitle(title, sessionId) }
        }
}
