package net.onefivefour.sessiontimer.core.defaults

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode.SEQUENCE
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.theme.TaskGroupColors

@Singleton
internal class DatabaseDefaultValuesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DatabaseDefaultValues {

    override fun getSessionTitle() = context.getString(R.string.default_session_title)

    override fun getTaskGroupTitle() = context.getString(R.string.default_taskgroup_title)

    override fun getTaskGroupColor() = TaskGroupColors().getAll().random().toArgb().toLong()

    override fun getTaskGroupPlayMode() = SEQUENCE

    override fun getTaskGroupNumberOfRandomTasks() = 1

    override fun getTaskTitle() = context.getString(R.string.default_task_title)

    override fun getTaskDuration() = 3
}
