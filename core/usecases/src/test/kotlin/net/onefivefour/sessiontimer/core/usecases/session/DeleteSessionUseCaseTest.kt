package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.test.NOW
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
internal class DeleteSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk(relaxed = true)

    private val taskGroupRepository: TaskGroupRepository = mockk(relaxed = true)

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private fun sut() = DeleteSessionUseCaseImpl(
        sessionRepository,
        taskGroupRepository,
        taskRepository
    )

    @Test
    fun `GIVEN a full session WHEN executing the UseCase THEN the session, all its taskGroups and tasks are deleted`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val taskGroupId1 = 2L
            val taskGroupId2 = 6L
            val createdAt = NOW
            coEvery { taskGroupRepository.getTaskGroupBySessionId(any()) } returns flowOf(
                listOf(
                    TaskGroup(
                        id = taskGroupId1,
                        title = "Task Group Title",
                        color = 0x00FF00,
                        playMode = PlayMode.N_TASKS_SHUFFLED,
                        tasks = listOf(
                            Task(
                                id = 3L,
                                title = "Task Title",
                                duration = 1.seconds,
                                sortOrder = 1,
                                taskGroupId = taskGroupId1,
                                createdAt = createdAt
                            ),
                            Task(
                                id = 4L,
                                title = "Task Title 2",
                                duration = 2.seconds,
                                sortOrder = 2,
                                taskGroupId = taskGroupId1,
                                createdAt = createdAt
                            ),
                            Task(
                                id = 5L,
                                title = "Task Title 3",
                                duration = 3.seconds,
                                sortOrder = 3,
                                taskGroupId = taskGroupId1,
                                createdAt = createdAt
                            )
                        ),
                        numberOfRandomTasks = 5,
                        defaultTaskDuration = 1.minutes,
                        sortOrder = 1,
                        sessionId = sessionId
                    ),
                    TaskGroup(
                        id = taskGroupId2,
                        title = "Task Group Title 2",
                        color = 0x00FFFF,
                        playMode = PlayMode.SEQUENCE,
                        tasks = listOf(
                            Task(
                                id = 7L,
                                title = "Task Title 7",
                                duration = 7.seconds,
                                sortOrder = 1,
                                taskGroupId = taskGroupId2,
                                createdAt = createdAt
                            ),
                            Task(
                                id = 8L,
                                title = "Task Title 8",
                                duration = 8.seconds,
                                sortOrder = 2,
                                taskGroupId = taskGroupId2,
                                createdAt = createdAt
                            ),
                            Task(
                                id = 9L,
                                title = "Task Title 9",
                                duration = 9.seconds,
                                sortOrder = 3,
                                taskGroupId = taskGroupId2,
                                createdAt = createdAt
                            )
                        ),
                        numberOfRandomTasks = 3,
                        defaultTaskDuration = 1.minutes,
                        sortOrder = 2,
                        sessionId = sessionId
                    )
                )
            )

            // WHEN
            sut().execute(sessionId)
            advanceUntilIdle()

            // THEN
            coVerify(ordering = Ordering.SEQUENCE) {
                taskGroupRepository.getTaskGroupBySessionId(sessionId)

                taskRepository.deleteTasksByTaskGroupId(taskGroupId1)
                taskGroupRepository.deleteTaskGroupById(taskGroupId1)

                taskRepository.deleteTasksByTaskGroupId(taskGroupId2)
                taskGroupRepository.deleteTaskGroupById(taskGroupId2)

                sessionRepository.deleteSessionById(sessionId)
            }
        }
}
