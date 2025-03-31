package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.test.FAKE_DB_DEFAULT_VALUES
import org.junit.Test

internal class NewTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()

    private fun sut() = NewTaskGroupUseCaseImpl(
        taskGroupRepository,
        FAKE_DB_DEFAULT_VALUES
    )

    @Test
    fun `GIVEN a sessionId WHEN executing the UseCase THEN it creates a new task group`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val taskGroupId = 123L
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

            coEvery {
                taskGroupRepository.getLastInsertId()
            } returns taskGroupId

            // WHEN
            sut().execute(sessionId)

            // THEN
            coVerify(exactly = 1) {
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
            }
        }
}
