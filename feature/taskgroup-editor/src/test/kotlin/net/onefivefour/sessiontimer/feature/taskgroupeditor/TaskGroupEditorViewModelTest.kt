package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.test.NOW
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupColorUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupDefaultTaskDurationUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupPlayModeUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.SetTaskGroupTitleUseCase
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.TaskGroupEditorRoute
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskGroupEditorViewModelTest {

    private val route = TaskGroupEditorRoute(taskGroupId = 1L)

    @get:Rule(order = 0)
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    @get:Rule(order = 1)
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val getTaskGroupUseCase: GetTaskGroupUseCase = mockk()

    private val setTaskGroupTitleUseCase: SetTaskGroupTitleUseCase = mockk()

    private val setTaskGroupColorUseCase: SetTaskGroupColorUseCase = mockk()

    private val setTaskGroupPlayModeUseCase: SetTaskGroupPlayModeUseCase = mockk()

    private val setTaskGroupDefaultTaskDurationUseCase: SetTaskGroupDefaultTaskDurationUseCase = mockk()

    private fun sut() = TaskGroupEditorViewModel(
        savedStateHandle = savedStateHandleRule.savedStateHandleMock,
        getTaskGroupUseCase = getTaskGroupUseCase,
        setTitle = setTaskGroupTitleUseCase,
        setColor = setTaskGroupColorUseCase,
        setPlayMode = setTaskGroupPlayModeUseCase,
        setDefaultTaskDuration = setTaskGroupDefaultTaskDurationUseCase
    )

    @Test
    fun `WHEN ViewModel is created THEN its uiState has correct initial value`() {
        // WHEN
        val sut = sut()

        // THEN
        assertThat(sut.uiState.value).isEqualTo(UiState.Initial)
    }

    @Test
    fun `GIVEN a taskGroup WHEN ViewModel is created THEN GetTaskGroupUseCase is called on init`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            coEvery { getTaskGroupUseCase.execute(taskGroupId) } returns flowOf(
                TaskGroup(
                    id = taskGroupId,
                    title = "TaskGroup 1",
                    color = 0xFFFF0000,
                    playMode = PlayMode.SEQUENCE,
                    numberOfRandomTasks = 3,
                    defaultTaskDuration = 1.minutes,
                    tasks = listOf(
                        Task(
                            id = 3L,
                            title = "Task 1",
                            duration = Duration.ZERO,
                            sortOrder = 1,
                            taskGroupId = taskGroupId,
                            createdAt = NOW
                        )
                    ),
                    sortOrder = 1,
                    sessionId = 2L
                )
            )

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { getTaskGroupUseCase.execute(taskGroupId) }

            sut.uiState.test {
                val uiState = awaitItem()
                check(uiState is UiState.Ready)

                val taskGroup = uiState.taskGroup
                assertThat(taskGroup.id).isEqualTo(taskGroupId)
                assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
                assertThat(taskGroup.color).isEqualTo(Color(0xFFFF0000))
                assertThat(taskGroup.playMode).isEqualTo(PlayMode.SEQUENCE)
                assertThat(taskGroup.numberOfRandomTasks).isEqualTo(3)
                assertThat(taskGroup.tasks).hasSize(1)

                val task = taskGroup.tasks[0]
                assertThat(task.id).isEqualTo(3L)
                assertThat(task.title).isEqualTo("Task 1")
            }
        }
}
