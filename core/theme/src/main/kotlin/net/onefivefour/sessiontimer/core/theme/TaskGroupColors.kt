package net.onefivefour.sessiontimer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TaskGroupColors(
    val color01: Color = Color(0xFFccaacb),
    val color02: Color = Color(0xFFffe1e8),
    val color03: Color = Color(0xFFd5a174),
    val color04: Color = Color(0xFFf1d99d),
    val color05: Color = Color(0xFFffff70),
    val color06: Color = Color(0xFF0ca73c),
    val color07: Color = Color(0xFFde000a),
    val color08: Color = Color(0xFFcf6a6f),
    val color09: Color = Color(0xFFff9589),
    val color10: Color = Color(0xFFffccb5),
    val color11: Color = Color(0xFF85e6ff),
    val color12: Color = Color(0xFF97c1a8),
    val color13: Color = Color(0xFF54cbcc),
    val color14: Color = Color(0xFFa2e1db),
    val color15: Color = Color(0xFF2e89b8),
    val color16: Color = Color(0xFF9db4ca),
    val color17: Color = Color(0xFFbed4eb),
    val color18: Color = Color(0xFFcce2cb)
) {
    fun getAll() = listOf(
        color01,
        color02,
        color03,
        color04,
        color05,
        color06,
        color07,
        color08,
        color09,
        color10,
        color11,
        color12,
        color13,
        color14,
        color15,
        color16,
        color17,
        color18
    )
}

val LocalTaskGroupColors = staticCompositionLocalOf {
    TaskGroupColors()
}

val MaterialTheme.taskGroupColors: TaskGroupColors
    @Composable
    @ReadOnlyComposable
    get() = LocalTaskGroupColors.current
