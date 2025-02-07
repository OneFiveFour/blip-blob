package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.database.test.FAKE_DB_DEFAULT_VALUES
import org.junit.Test

internal class NewSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()

    private val taskGroupRepository: TaskGroupRepository = mockk()

    private val taskRepository: TaskRepository = mockk()

    private fun sut() = NewSessionUseCaseImpl(
        sessionRepository,
        taskGroupRepository,
        taskRepository,
        FAKE_DB_DEFAULT_VALUES
    )

    @Test
    fun `WHEN executing the UseCase THEN a new session with taskGroup and task is created`() =
        runTest {
            val sessionId = 1L
            val taskGroupId = 2L
            coEvery { sessionRepository.newSession(any()) } returns Unit
            coEvery { sessionRepository.getLastInsertId() } returns sessionId
            coEvery {
                taskGroupRepository.newTaskGroup(
                    title = any(),
                    color = any(),
                    onColor = any(),
                    playMode = any(),
                    numberOfRandomTasks = any(),
                    defaultTaskDuration = any(),
                    sessionId = any()
                )
            } returns Unit
            coEvery { taskGroupRepository.getLastInsertId() } returns taskGroupId
            coEvery { taskRepository.newTask(any(), any(), any()) } returns Unit

            // WHEN
            sut().execute()

            // THEN
            coVerify(ordering = Ordering.ORDERED) {
                sessionRepository.newSession(FAKE_DB_DEFAULT_VALUES.getSessionTitle())

                val color = FAKE_DB_DEFAULT_VALUES.getTaskGroupColors()
                taskGroupRepository.newTaskGroup(
                    title = FAKE_DB_DEFAULT_VALUES.getTaskGroupTitle(),
                    color = color.first,
                    onColor = color.second,
                    playMode = FAKE_DB_DEFAULT_VALUES.getTaskGroupPlayMode(),
                    numberOfRandomTasks = FAKE_DB_DEFAULT_VALUES.getTaskGroupNumberOfRandomTasks(),
                    defaultTaskDuration = FAKE_DB_DEFAULT_VALUES.getTaskGroupDefaultTaskDuration(),
                    sessionId = sessionId
                )
                taskRepository.newTask(
                    FAKE_DB_DEFAULT_VALUES.getTaskTitle(),
                    FAKE_DB_DEFAULT_VALUES.getTaskDuration(),
                    taskGroupId
                )
            }
        }
}
