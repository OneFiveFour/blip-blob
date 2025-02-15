package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

internal val LocalTaskEditMode = compositionLocalOf {
    mutableStateOf<TaskEditMode>(TaskEditMode.None)
}
