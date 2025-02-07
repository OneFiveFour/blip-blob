package net.onefivefour.sessiontimer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TaskGroupColors(
    val color01: Pair<Color, Color> = Color(0xFFccaacb) to Color(0xFF1F3701),
    val color02: Pair<Color, Color> = Color(0xFFffe1e8) to Color(0xFF1F3701),
    val color03: Pair<Color, Color> = Color(0xFFd5a174) to Color(0xFF1F3701),
    val color04: Pair<Color, Color> = Color(0xFFf1d99d) to Color(0xFF1F3701),
    val color05: Pair<Color, Color> = Color(0xFFffff70) to Color(0xFF1F3701),
    val color06: Pair<Color, Color> = Color(0xFF0ca73c) to Color(0xFF1F3701),
    val color07: Pair<Color, Color> = Color(0xFFde000a) to Color(0xFF1F3701),
    val color08: Pair<Color, Color> = Color(0xFFcf6a6f) to Color(0xFF1F3701),
    val color09: Pair<Color, Color> = Color(0xFFff9589) to Color(0xFF1F3701),
    val color10: Pair<Color, Color> = Color(0xFFffccb5) to Color(0xFF1F3701),
    val color11: Pair<Color, Color> = Color(0xFF85e6ff) to Color(0xFF1F3701),
    val color12: Pair<Color, Color> = Color(0xFF97c1a8) to Color(0xFF1F3701),
    val color13: Pair<Color, Color> = Color(0xFF54cbcc) to Color(0xFF1F3701),
    val color14: Pair<Color, Color> = Color(0xFFa2e1db) to Color(0xFF1F3701),
    val color15: Pair<Color, Color> = Color(0xFF2e89b8) to Color(0xFF1F3701)
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
        color15
    )
}

val LocalTaskGroupColors = staticCompositionLocalOf {
    TaskGroupColors()
}

val MaterialTheme.taskGroupColors: TaskGroupColors
    @Composable
    @ReadOnlyComposable
    get() = LocalTaskGroupColors.current
