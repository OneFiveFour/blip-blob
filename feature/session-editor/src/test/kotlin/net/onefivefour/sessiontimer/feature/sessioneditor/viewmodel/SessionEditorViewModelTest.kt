package net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.test.NOW
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionTitleUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.NewTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskDurationUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskSortOrdersUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.SetTaskTitleUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupSortOrdersUseCase
import net.onefivefour.sessiontimer.feature.sessioneditor.api.SessionEditorRoute
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionEditorViewModelTest {

    private val route = SessionEditorRoute(sessionId = 1L)

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    @get:Rule
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val getSessionUseCase: GetSessionUseCase = mockk()

    private val newTaskGroupUseCase: NewTaskGroupUseCase = mockk()

    private val newTaskUseCase: NewTaskUseCase = mockk()

    private val deleteTaskUseCase: DeleteTaskUseCase = mockk()

    private val deleteTaskGroupUseCase: DeleteTaskGroupUseCase = mockk()

    private val setTaskTitleUseCase: SetTaskTitleUseCase = mockk()

    private val setTaskGroupSortOrdersUseCase: SetTaskGroupSortOrdersUseCase = mockk()

    private val setTaskSortOrdersUseCase: SetTaskSortOrdersUseCase = mockk()

    private val setTaskDurationUseCase: SetTaskDurationUseCase = mockk()

    private val setSessionTitleUseCase: SetSessionTitleUseCase = mockk()

    private fun sut() = SessionEditorViewModel(
        savedStateHandle = savedStateHandleRule.savedStateHandleMock,
        getSessionUseCase = getSessionUseCase,
        newTaskGroupUseCase = newTaskGroupUseCase,
        newTaskUseCase = newTaskUseCase,
        deleteTaskUseCase = deleteTaskUseCase,
        deleteTaskGroupUseCase = deleteTaskGroupUseCase,
        setTaskTitleUseCase = setTaskTitleUseCase,
        setTaskDurationUseCase = setTaskDurationUseCase,
        setTaskGroupSortOrdersUseCase = setTaskGroupSortOrdersUseCase,
        setTaskSortOrdersUseCase = setTaskSortOrdersUseCase,
        setSessionTitleUseCase = setSessionTitleUseCase
    )

    @Test
    fun `GIVEN a session WHEN creating the ViewModel THEN its initial state is correct`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(
                Session(1L, "Session 1", 1, emptyList(), NOW)
            )

            // WHEN
            val sut = sut()

            // THEN
            sut.uiState.test {
                val uiState1 = awaitItem()
                assertThat(uiState1 is UiState.Initial).isTrue()
            }
        }

    @Test
    fun `GIVEN a session WHEN creating the ViewModel THEN the GetSessionUseCase is executed and the UiState is Success`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(
                Session(
                    id = sessionId,
                    title = "Session 1",
                    sortOrder = 1,
                    taskGroups = listOf(
                        TaskGroup(
                            id = 1L,
                            title = "TaskGroup Title",
                            color = 0xFFFF0000,
                            onColor = 0xFFFFFF00,
                            playMode = PlayMode.SEQUENCE,
                            numberOfRandomTasks = 1,
                            defaultTaskDuration = 1.minutes,
                            sortOrder = 1,
                            sessionId = sessionId,
                            tasks = emptyList()
                        )
                    ),
                    createdAt = NOW
                )
            )

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { getSessionUseCase.execute(sessionId) }

            sut.uiState.test {
                val uiState = awaitItem()
                check(uiState is UiState.Ready)
                val session = uiState.uiSession
                assertThat(session.taskGroups).hasSize(1)
            }
        }

    @Test
    fun `GIVEN no session WHEN creating the ViewModel THEN the GetSessionUseCase is executed and the UiState is Error`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(null)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { getSessionUseCase.execute(any()) }

            sut.uiState.test {
                val uiState = awaitItem()
                check(uiState is UiState.Error)
            }
        }

    @Test
    fun `GIVEN a session WHEN newTaskGroup is called THEN NewTaskGroupUseCase is executed`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(
                Session(sessionId, "Session 1", 1, emptyList(), NOW)
            )
            coEvery { newTaskGroupUseCase.execute(any()) } returns Unit

            // WHEN
            val sut = sut()
            sut.onAction(SessionEditorAction.CreateTaskGroup)
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                newTaskGroupUseCase.execute(sessionId)
            }
        }

    @Test
    fun `GIVEN a session WHEN deleteTaskGroup is called THEN DeleteTaskGroupUseCase is executed`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val taskGroupId = 2L
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(
                Session(sessionId, "Session 1", 1, emptyList(), NOW)
            )
            coEvery { deleteTaskGroupUseCase.execute(any()) } returns Unit

            // WHEN
            val sut = sut()
            sut.onAction(SessionEditorAction.DeleteTaskGroup(taskGroupId))
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                deleteTaskGroupUseCase.execute(taskGroupId)
            }
        }

    @Test
    fun `GIVEN a session WHEN newTask is called THEN NewTaskUseCase is executed`() = runTest {
        // GIVEN
        val sessionId = 1L
        val taskGroupId = 2L
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", 1, emptyList(), NOW)
        )
        coEvery { newTaskUseCase.execute(any()) } returns Unit

        // WHEN
        val sut = sut()
        sut.onAction(SessionEditorAction.CreateTask(taskGroupId))
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) {
            newTaskUseCase.execute(taskGroupId)
        }
    }

    @Test
    fun `GIVEN a session WHEN deleteTask is called THEN DeleteTaskUseCase is executed`() = runTest {
        // GIVEN
        val sessionId = 1L
        val taskId = 2L
        val taskGroupId = 3L
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", 1, emptyList(), NOW)
        )
        coEvery { deleteTaskUseCase.execute(any(), taskGroupId) } returns Unit

        // WHEN
        val sut = sut()
        sut.onAction(SessionEditorAction.DeleteTask(taskId, taskGroupId))
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) {
            deleteTaskUseCase.execute(taskId, taskGroupId)
        }
    }
}
