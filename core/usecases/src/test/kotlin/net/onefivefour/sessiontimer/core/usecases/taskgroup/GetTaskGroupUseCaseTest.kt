package net.onefivefour.sessiontimer.core.usecases.taskgroup

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import org.junit.Test

internal class GetTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()

    private fun sut() = GetTaskGroupUseCaseImpl(
        taskGroupRepository
    )

    @Test
    fun `GIVEN a taskGroupId WHEN executing the UseCase THEN the correct task group is returned`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            coEvery { taskGroupRepository.getTaskGroupById(taskGroupId) } returns flowOf(
                TaskGroup(
                    id = taskGroupId,
                    title = "TaskGroup 1",
                    color = 0xFF0000,
                    onColor = 0xFFFF00,
                    playMode = PlayMode.N_TASKS_SHUFFLED,
                    tasks = emptyList(),
                    numberOfRandomTasks = 5,
                    defaultTaskDuration = 1.minutes,
                    sortOrder = 1,
                    sessionId = 2L
                )
            )

            // WHEN
            val result = sut().execute(taskGroupId)

            // THEN
            result.test {
                val taskGroup = awaitItem()
                assertThat(taskGroup.id).isEqualTo(taskGroupId)
                assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
                assertThat(taskGroup.color).isEqualTo(0xFF0000)
                assertThat(taskGroup.playMode).isEqualTo(PlayMode.N_TASKS_SHUFFLED)
                assertThat(taskGroup.numberOfRandomTasks).isEqualTo(5)
                assertThat(taskGroup.tasks).isEmpty()
                assertThat(taskGroup.sessionId).isEqualTo(2L)
                awaitComplete()
            }

            coVerify(exactly = 1) {
                taskGroupRepository.getTaskGroupById(taskGroupId)
            }
        }
}
