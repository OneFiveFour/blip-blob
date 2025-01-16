package net.onefivefour.sessiontimer.feature.sessioneditor.model

import net.onefivefour.sessiontimer.core.common.domain.model.Session

internal data class UiSession(
    val taskGroups: List<UiTaskGroup>
)

internal fun Session.toUiSession() : UiSession {
    return UiSession(
        this.taskGroups.toUiTaskGroups()
    )
}
