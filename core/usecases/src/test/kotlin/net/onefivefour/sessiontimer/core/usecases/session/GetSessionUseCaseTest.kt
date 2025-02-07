package net.onefivefour.sessiontimer.core.usecases.session

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.test.NOW
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import org.junit.Test
import kotlin.time.Duration.Companion.minutes

internal class GetSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()

    private fun sut() = GetSessionUseCaseImpl(
        sessionRepository
    )

    @Test
    fun `GIVEN a full session WHEN executing the UseCase THEN a session with all its task groups and tasks is returned`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val taskGroupId = 2L
            coEvery { sessionRepository.getSession(any()) } returns flowOf(
                Session(
                    id = sessionId,
                    title = "Session 1",
                    taskGroups = listOf(
                        TaskGroup(
                            id = taskGroupId,
                            title = "TaskGroup 1",
                            color = 0xFF0000,
                            onColor = 0xFFFF00,
                            playMode = PlayMode.N_TASKS_SHUFFLED,
                            sortOrder = 1,
                            tasks = listOf(
                                Task(
                                    id = 3L,
                                    title = "Task 1",
                                    duration = 3.seconds,
                                    sortOrder = 1,
                                    taskGroupId = taskGroupId,
                                    createdAt = NOW
                                )
                            ),
                            numberOfRandomTasks = 5,
                            defaultTaskDuration = 1.minutes,
                            sessionId = sessionId
                        )
                    ),
                    sortOrder = 1,
                    createdAt = NOW
                )
            )

            // WHEN
            val fullSessionFlow = sut().execute(sessionId)

            // THEN
            fullSessionFlow.test {
                val fullSession = awaitItem()
                checkNotNull(fullSession)
                assertThat(fullSession.id).isEqualTo(sessionId)
                assertThat(fullSession.title).isEqualTo("Session 1")
                assertThat(fullSession.taskGroups).hasSize(1)

                val taskGroup = fullSession.taskGroups.first()
                assertThat(taskGroup.id).isEqualTo(taskGroupId)
                assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
                assertThat(taskGroup.color).isEqualTo(0xFF0000)
                assertThat(taskGroup.playMode).isEqualTo(PlayMode.N_TASKS_SHUFFLED)
                assertThat(taskGroup.numberOfRandomTasks).isEqualTo(5)
                assertThat(taskGroup.sessionId).isEqualTo(sessionId)
                assertThat(taskGroup.tasks).hasSize(1)

                val task = taskGroup.tasks.first()
                assertThat(task.id).isEqualTo(3L)
                assertThat(task.title).isEqualTo("Task 1")
                assertThat(task.duration).isEqualTo(3.seconds)
                assertThat(task.taskGroupId).isEqualTo(taskGroup.id)

                awaitComplete()
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN calling the UseCase THEN an empty taskGroup must be part of the full session object`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            coEvery { sessionRepository.getSession(any()) } returns flowOf(
                Session(
                    id = sessionId,
                    title = "Session 1",
                    sortOrder = 1,
                    taskGroups = emptyList(),
                    createdAt = NOW
                )
            )

            // WHEN
            val result = sut().execute(sessionId)

            // THEN
            result.test {
                val session = awaitItem()
                checkNotNull(session)
                assertThat(session.taskGroups).isEmpty()

                awaitComplete()
            }
        }
}
