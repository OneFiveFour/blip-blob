package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.DenormalizedTaskGroupView
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource
import org.junit.Test

internal class TaskGroupRepositoryImplTest {

    private val taskGroupDataSource: TaskGroupDataSource = mockk()

    private fun sut() = TaskGroupRepositoryImpl(
        taskGroupDataSource
    )

    @Test
    fun `GIVEN task group data WHEN newTaskGroup is called THEN the call is delegated to taskGroupDataSource`() =
        runTest {
            // GIVEN
            coEvery { taskGroupDataSource.insert(any(), any(), any(), any(), any()) } returns Unit
            val title = "Sample Task Group"
            val color = 0xFF0000L
            val playMode = PlayMode.SEQUENCE
            val numberOfRandomTasks = 3
            val sessionId = 1L

            // WHEN
            sut().newTaskGroup(title, color, playMode, numberOfRandomTasks, sessionId)

            // THEN
            coVerify(exactly = 1) {
                taskGroupDataSource.insert(
                    title = title,
                    color = color,
                    playMode = playMode.toString(),
                    numberOfRandomTasks = numberOfRandomTasks.toLong(),
                    sessionId = sessionId
                )
            }
        }

    @Test
    fun `GIVEN a taskGroupId WHEN getTaskGroupById is called THEN the mapped DomainTaskGroup should be returned`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            val denormalizedTaskGroup = listOf(
                DenormalizedTaskGroupView(
                    taskGroupId = 1L,
                    taskGroupTitle = "Task Group 1",
                    taskGroupColor = 0xFF00FFL,
                    taskGroupPlayMode = PlayMode.N_TASKS_SHUFFLED.toString(),
                    taskGroupNumberOfRandomTasks = 2L,
                    taskGroupSortOrder = 1,
                    sessionId = 3,
                    taskId = 1L,
                    taskTitle = "Task 1",
                    taskDuration = 10,
                    taskSortOrder = 1
                ),
                DenormalizedTaskGroupView(
                    taskGroupId = 1L,
                    taskGroupTitle = "Task Group 1",
                    taskGroupColor = 0xFF00FFL,
                    taskGroupPlayMode = PlayMode.N_TASKS_SHUFFLED.toString(),
                    taskGroupNumberOfRandomTasks = 2L,
                    taskGroupSortOrder = 2,
                    sessionId = 3,
                    taskId = 2L,
                    taskTitle = "Task 2",
                    taskDuration = 20,
                    taskSortOrder = 1
                )
            )
            coEvery { taskGroupDataSource.getDenormalizedTaskGroup(taskGroupId) } returns flowOf(
                denormalizedTaskGroup
            )

            // WHEN
            val taskGroup = sut().getTaskGroupById(taskGroupId)

            // THEN
            taskGroup.test {
                val result = awaitItem()
                assertThat(result).isEqualTo(denormalizedTaskGroup.toDomainTaskGroup())
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN getTaskGroupBySessionId is called THEN the mapped DomainTaskGroup should be returned`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val databaseTaskGroups = listOf(
                DatabaseTaskGroup(
                    id = 1L,
                    title = "Task Group 1",
                    color = 0xFF00FFL,
                    playMode = PlayMode.N_TASKS_SHUFFLED.toString(),
                    numberOfRandomTasks = 2L,
                    sortOrder = 1L,
                    sessionId = sessionId
                ),
                DatabaseTaskGroup(
                    id = 2L,
                    title = "Task Group 2",
                    color = 0x00FFFFL,
                    playMode = PlayMode.SEQUENCE.toString(),
                    numberOfRandomTasks = 1L,
                    sortOrder = 2L,
                    sessionId = sessionId
                )
            )
            coEvery { taskGroupDataSource.getBySessionId(sessionId) } returns flowOf(
                databaseTaskGroups
            )

            // WHEN
            val taskGroup = sut().getTaskGroupBySessionId(sessionId)

            // THEN
            taskGroup.test {
                val result = awaitItem()
                assertThat(result).isEqualTo(databaseTaskGroups.map { it.toDomainTaskGroup() })
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN taskGroup data WHEN updateTaskGroup is called THEN the call is delegated to taskGroupDataSource`() =
        runTest {
            // GIVEN
            coEvery { taskGroupDataSource.update(any(), any(), any(), any(), any(), any()) } returns Unit
            val taskGroupId = 1L
            val title = "Updated Task Group"
            val color = 0x00FF00
            val playMode = PlayMode.SEQUENCE
            val numberOfRandomTasks = 4
            val sortOrder = 1

            // WHEN
            sut().updateTaskGroup(
                taskGroupId = taskGroupId,
                title = title,
                color = color,
                playMode = playMode,
                numberOfRandomTasks = numberOfRandomTasks,
                sortOrder = sortOrder
            )

            // THEN
            coVerify(exactly = 1) {
                taskGroupDataSource.update(
                    taskGroupId = taskGroupId,
                    title = title,
                    color = color.toLong(),
                    playMode = playMode.toString(),
                    numberOfRandomTasks = numberOfRandomTasks.toLong(),
                    sortOrder = sortOrder.toLong()
                )
            }
        }

    @Test
    fun `GIVEN a taskGroupId WHEN deleteTaskGroupById is called THEN the call is delegated to taskGroupDataSource`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            coEvery { taskGroupDataSource.deleteById(any()) } returns Unit

            // WHEN
            sut().deleteTaskGroupById(taskGroupId)

            // THEN
            coVerify(exactly = 1) { taskGroupDataSource.deleteById(taskGroupId) }
        }

    @Test
    fun `GIVEN a last inserted id WHEN getLastInsertId is called THEN the value from taskGroupDataSource should be returned`() =
        runTest {
            // GIVEN
            val lastInsertId = 42L
            coEvery { taskGroupDataSource.getLastInsertId() } returns lastInsertId

            // WHEN
            val result = sut().getLastInsertId()

            // THEN
            assertThat(result).isEqualTo(lastInsertId)
        }
}
