package net.onefivefour.sessiontimer.core.ui.modifier

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/**
 * Adds a border in a random color to the view.
 */
fun Modifier.debugBorder(): Modifier {
    return this.border(2.dp, generateRandomColor())
}

private fun generateRandomColor(): Color {
    val red = Random.nextInt(50, 205)
    val green = Random.nextInt(50, 205)
    val blue = Random.nextInt(50, 205)
    return Color(red, green, blue)
}