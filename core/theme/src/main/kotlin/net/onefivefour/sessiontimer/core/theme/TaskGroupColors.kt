package net.onefivefour.sessiontimer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TaskGroupColors(
    val color01: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color02: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color03: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color04: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color05: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color06: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color07: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color08: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color09: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color10: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color11: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color12: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color13: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color14: Pair<Color, Color> = Color.Unspecified to Color.Unspecified,
    val color15: Pair<Color, Color> = Color.Unspecified to Color.Unspecified
)

val LocalTaskGroupColors = staticCompositionLocalOf {
    TaskGroupColors()
}

val MaterialTheme.taskGroupColors: TaskGroupColors
    @Composable
    @ReadOnlyComposable
    get() = LocalTaskGroupColors.current

internal val TaskGroupColorsLight = TaskGroupColors(
    color01 = Color(0xFF1D275D) to Color(0xFFFFFFFF),
    color02 = Color(0xFF38247B) to Color(0xFFFFFFFF),
    color03 = Color(0xFF6E2999) to Color(0xFFFFFFFF),
    color04 = Color(0xFFB92DB9) to Color(0xFFFFFFFF),
    color05 = Color(0xFF611F44) to Color(0xFFFFFFFF),
    color06 = Color(0xFF7E2535) to Color(0xFFFFFFFF),
    color07 = Color(0xFF9D3F2A) to Color(0xFFFFFFFF),
    color08 = Color(0xFFBD7F2E) to Color(0xFFFFFFFF),
    color09 = Color(0xFF6F4A1A) to Color(0xFFFFFFFF),
    color10 = Color(0xFF686920) to Color(0xFFFFFFFF),
    color11 = Color(0xFF608726) to Color(0xFFFFFFFF),
    color12 = Color(0xFF2FC648) to Color(0xFFFFFFFF),
    color13 = Color(0xFF227253) to Color(0xFFFFFFFF),
    color14 = Color(0xFF279090) to Color(0xFFFFFFFF),
    color15 = Color(0xFF2C7DAF) to Color(0xFFFFFFFF),
)

internal val TaskGroupColorsDark = TaskGroupColors(
    color01 = Color(0xFF0D1C6D) to Color(0xFFFFFFFF),
    color02 = Color(0xFF7614CC) to Color(0xFFFFFFFF),
    color03 = Color(0xFFFBB1B9) to Color(0xFF1F3701),
    color04 = Color(0xFFF254D0) to Color(0xFFFFFFFF),
    color05 = Color(0xFF841048) to Color(0xFFFFFFFF),
    color06 = Color(0xFFE53C15) to Color(0xFFFFFFFF),
    color07 = Color(0xFFF5E36B) to Color(0xFF1F3701),
    color08 = Color(0xFFE0FCC9) to Color(0xFF1F3701),
    color09 = Color(0xFF82F7C2) to Color(0xFF1F3701),
    color10 = Color(0xFFE2F7FE) to Color(0xFF1F3701),
    color11 = Color(0xFF849C11) to Color(0xFFFFFFFF),
    color12 = Color(0xFF43EC27) to Color(0xFF1F3701),
    color13 = Color(0xFF13B49B) to Color(0xFFFFFFFF),
    color14 = Color(0xFFAE9AF9) to Color(0xFFFFFFFF),
    color15 = Color(0xFF3E91EF) to Color(0xFFFFFFFF),
)

class TaskGroupColorProvider(isDarkTheme: Boolean) {
    private val colors = if (isDarkTheme) TaskGroupColorsDark else TaskGroupColorsLight // Change this to TaskGroupColorsDark for dark theme

    fun getAll() = listOf(
        colors.color01, colors.color02, colors.color03, colors.color04, colors.color05,
        colors.color06, colors.color07, colors.color08, colors.color09, colors.color10,
        colors.color11, colors.color12, colors.color13, colors.color14, colors.color15
    )

    fun getRandomColor(): Pair<Color, Color> {
        val colorList = getAll()
        return colorList.random()
    }
}

