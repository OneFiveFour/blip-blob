package net.onefivefour.sessiontimer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TaskGroupColors(
    val color01: Color,
    val color02: Color,
    val color03: Color,
    val color04: Color,
    val color05: Color,
    val color06: Color,
    val color07: Color,
    val color08: Color,
    val color09: Color,
    val color10: Color,
    val color11: Color,
    val color12: Color,
    val color13: Color,
    val color14: Color,
    val color15: Color,
    val color16: Color,
    val color17: Color,
    val color18: Color,
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
    TaskGroupColors(
        color01 = Color.Unspecified,
        color02 = Color.Unspecified,
        color03 = Color.Unspecified,
        color04 = Color.Unspecified,
        color05 = Color.Unspecified,
        color06 = Color.Unspecified,
        color07 = Color.Unspecified,
        color08 = Color.Unspecified,
        color09 = Color.Unspecified,
        color10 = Color.Unspecified,
        color11 = Color.Unspecified,
        color12 = Color.Unspecified,
        color13 = Color.Unspecified,
        color14 = Color.Unspecified,
        color15 = Color.Unspecified,
        color16 = Color.Unspecified,
        color17 = Color.Unspecified,
        color18 = Color.Unspecified
    )
}

val MaterialTheme.taskGroupColors: TaskGroupColors
    @Composable
    @ReadOnlyComposable
    get() = LocalTaskGroupColors.current

internal val TaskGroupColorsLight = TaskGroupColors(
    color01 = Color(0xFFccaacb),
    color02 = Color(0xFFffe1e8),
    color03 = Color(0xFFd5a174),
    color04 = Color(0xFFf1d99d),
    color05 = Color(0xFFffff70),
    color06 = Color(0xFF0ca73c),

    color07 = Color(0xFFde000a),
    color08 = Color(0xFFcf6a6f),
    color09 = Color(0xFFff9589),
    color10 = Color(0xFFffccb5),
    color11 = Color(0xFF85e6ff),
    color12 = Color(0xFF97c1a8),

    color13 = Color(0xFF54cbcc),
    color14 = Color(0xFFa2e1db),
    color15 = Color(0xFF2e89b8),
    color16 = Color(0xFF9db4ca),
    color17 = Color(0xFFbed4eb),
    color18 = Color(0xFFcce2cb)
)

internal val TaskGroupColorsDark = TaskGroupColors(
    color01 = Color(0xFFccaacb),
    color02 = Color(0xFFffe1e8),
    color03 = Color(0xFFd5a174),
    color04 = Color(0xFFf1d99d),
    color05 = Color(0xFFffff70),
    color06 = Color(0xFF0ca73c),
    color07 = Color(0xFFde000a),
    color08 = Color(0xFFcf6a6f),
    color09 = Color(0xFFff9589),
    color10 = Color(0xFFffccb5),
    color11 = Color(0xFF85e6ff),
    color12 = Color(0xFF97c1a8),
    color13 = Color(0xFF54cbcc),
    color14 = Color(0xFFa2e1db),
    color15 = Color(0xFF2e89b8),
    color16 = Color(0xFF9db4ca),
    color17 = Color(0xFFbed4eb),
    color18 = Color(0xFFcce2cb)
)