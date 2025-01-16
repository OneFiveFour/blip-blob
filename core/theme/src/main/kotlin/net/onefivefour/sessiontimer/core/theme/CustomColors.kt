package net.onefivefour.sessiontimer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val surfaceGlow: Color
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        surfaceGlow = Color.Unspecified
    )
}

val MaterialTheme.customColors: CustomColors
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current

internal val CustomColorsLight = CustomColors(
    surfaceGlow = Color(0xFFE0E0E0)
)

internal val CustomColorsDark = CustomColors(
    surfaceGlow = Color(0xFF535353)
)