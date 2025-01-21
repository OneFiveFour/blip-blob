package net.onefivefour.sessiontimer.feature.sessionoverview

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.test.NOW
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.usecases.api.session.DeleteSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.GetAllSessionsUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.NewSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionSortOrdersUseCase
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionOverviewViewModelTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val getAllSessionsUseCase: GetAllSessionsUseCase = mockk()

    private val newSessionUseCase: NewSessionUseCase = mockk()

    private val setSessionSortOrdersUseCase: SetSessionSortOrdersUseCase = mockk()

    private val deleteSessionUseCase: DeleteSessionUseCase = mockk()

    private fun sut() = SessionOverviewViewModel(
        getAllSessionsUseCase,
        newSessionUseCase,
        setSessionSortOrdersUseCase,
        deleteSessionUseCase
    )

    @Test
    fun `WHEN ViewModel is created THEN uiState has correct initial value`() {
        // WHEN
        val sut = sut()

        // THEN
        assertThat(sut.uiState.value).isEqualTo(UiState.Initial)
    }

    @Test
    fun `WHEN ViewModel is created THEN GetAllSessionsUseCase is executed`() = runTest {
        // GIVEN
        coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())

        // WHEN
        sut()
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) { getAllSessionsUseCase.execute() }
    }

    @Test
    fun `GIVEN two sessions WHEN ViewModel is created THEN session data is updated in uiState after init`() =
        runTest {
            // GIVEN
            val sessions = listOf(
                Session(id = 1, title = "Session 1", 1, emptyList(), NOW),
                Session(id = 2, title = "Session 2", 2, emptyList(), NOW)
            )
            coEvery { getAllSessionsUseCase.execute() } returns flowOf(sessions)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            val expectedUiState = UiState.Success(sessions = sessions.toUiSessions())
            assertThat(sut.uiState.value).isEqualTo(expectedUiState)
        }

    @Test
    fun `GIVEN a ViewModel WHEN newSession is called THEN NewSessionUseCase is executed`() =
        runTest {
            // GIVEN
            coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
            coEvery { newSessionUseCase.execute() } returns Unit
            val sut = sut()

            // WHEN
            sut.onAction(SessionOverviewAction.CreateSession)
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                newSessionUseCase.execute()
            }
        }

    @Test
    fun `GIVEN session data WHEN setSessionSortOrder is called THEN UpdateSessionUseCase is executed with that data`() =
        runTest {
            // GIVEN
            coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
            coEvery { setSessionSortOrdersUseCase.execute(any()) } returns Unit
            val sessionIds = listOf(1L)

            // WHEN
            val sut = sut()
            sut.onAction(SessionOverviewAction.UpdateSessionSortOrders(sessionIds))
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                setSessionSortOrdersUseCase.execute(sessionIds)
            }
        }
}
