package net.onefivefour.sessiontimer.core.ui.utils

import androidx.compose.ui.graphics.Color

fun darkenColor(color: Color): Color {
    return color.copy(
        alpha = 1f,
        red = color.red / 2,
        green = color.green / 2,
        blue = color.blue / 2
    )
}
