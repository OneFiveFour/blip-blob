package net.onefivefour.sessiontimer.feature.sessioneditor.model

import net.onefivefour.sessiontimer.core.common.domain.model.Session

internal data class UiSession(
    val title: String,
    val taskGroups: List<UiTaskGroup>
)

internal fun Session.toUiSession(): UiSession {
    return UiSession(
        title = this.title,
        taskGroups = this.taskGroups.toUiTaskGroups()
    )
}
