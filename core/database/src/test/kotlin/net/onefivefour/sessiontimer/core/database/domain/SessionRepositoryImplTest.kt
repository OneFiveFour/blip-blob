package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.test.NOW_MILLIS
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.DenormalizedSessionView
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import org.junit.Test

internal class SessionRepositoryImplTest {

    private val sessionDataSource: SessionDataSource = mockk()

    private fun sut() = SessionRepositoryImpl(
        sessionDataSource
    )

    @Test
    fun `GIVEN a sessionTitle WHEN newSession is called THEN the call is delegated to sessionDataSource`() =
        runTest {
            // GIVEN
            coEvery { sessionDataSource.insert(any()) } returns Unit
            val title = "Sample Session"

            // WHEN
            sut().newSession(title)

            // THEN
            coVerify(exactly = 1) { sessionDataSource.insert(title) }
        }

    @Test
    fun `GIVEN a list of sessions WHEN getAllSessions is called THEN the mapped DomainSessions should be returned`() =
        runTest {
            // GIVEN
            val databaseSessions = listOf(
                DatabaseSession(
                    id = 1L,
                    title = "Session 1",
                    sortOrder = 1L,
                    createdAt = NOW_MILLIS
                ),
                DatabaseSession(
                    id = 2L,
                    title = "Session 2",
                    sortOrder = 2L,
                    createdAt = NOW_MILLIS
                )
            )
            coEvery { sessionDataSource.getAll() } returns flowOf(databaseSessions)

            // WHEN
            val domainSessions = sut().getAllSessions()

            // THEN
            domainSessions.test {
                val result = awaitItem()
                assertThat(result).isEqualTo(databaseSessions.map { it.toDomainSession() })
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN getDenormalizedSessionView is called THEN the mapped DomainSession should be returned`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val denormalizedSessionView = DenormalizedSessionView(
                sessionId = sessionId,
                sessionTitle = "Session 1",
                sessionSortOrder = 1L,
                sessionCreatedAt = NOW_MILLIS,
                taskGroupId = 2L,
                taskGroupTitle = "Task Group 1",
                taskGroupColor = 0xFF00FFL,
                taskGroupPlayMode = PlayMode.N_TASKS_SHUFFLED.toString(),
                taskGroupNumberOfRandomTasks = 3,
                taskGroupSortOrder = 1L,
                taskId = 1L,
                taskTaskGroupId = 1L,
                taskTitle = "Task 1",
                taskDuration = 300,
                taskSortOrder = 1L,
                taskCreatedAt = NOW_MILLIS
            )
            coEvery { sessionDataSource.getDenormalizedSession(sessionId) } returns flowOf(
                listOf(denormalizedSessionView)
            )

            // WHEN
            val session = sut().getSession(sessionId)

            // THEN
            session.test {
                val result = awaitItem()
                assertThat(result).isEqualTo(listOf(denormalizedSessionView).toDomainSession())
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN deleteSessionById is called THEN the call is delegated to sessionDataSource`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            coEvery { sessionDataSource.deleteById(any()) } returns Unit

            // WHEN
            sut().deleteSessionById(sessionId)

            // THEN
            coVerify(exactly = 1) { sessionDataSource.deleteById(sessionId) }
        }

    @Test
    fun `GIVEN session data WHEN setTitle is called THEN the call is delegated to sessionDataSource`() =
        runTest {
            // GIVEN
            coEvery { sessionDataSource.setTitle(any(), any()) } returns Unit
            val sessionId = 1L
            val title = "Updated Session Title"

            // WHEN
            sut().setSessionTitle(
                sessionId = sessionId,
                title = title
            )

            // THEN
            coVerify(exactly = 1) {
                sessionDataSource.setTitle(
                    sessionId = sessionId,
                    title = title
                )
            }
        }

    @Test
    fun `GIVEN a last inserted sessionId WHEN getLastInsertId is called THEN the value from sessionDataSource should be returned`() =
        runTest {
            // GIVEN
            val lastInsertId = 42L
            coEvery { sessionDataSource.getLastInsertId() } returns lastInsertId

            // WHEN
            val result = sut().getLastInsertId()

            // THEN
            assertThat(result).isEqualTo(lastInsertId)
        }
}
