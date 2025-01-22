package net.onefivefour.sessiontimer.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    error = errorLight,
    onError = onErrorLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    surfaceVariant = surfaceVariantLight,
    surfaceDim = surfaceDimLight,
    onSurface = onSurfaceLight,
    onSurfaceVariant = onSurfaceVariantLight
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    error = errorDark,
    onError = onErrorDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    surfaceVariant = surfaceVariantDark,
    surfaceDim = surfaceDimDark,
    onSurface = onSurfaceDark,
    onSurfaceVariant = onSurfaceVariantDark
)

@Composable
fun SessionTimerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val customColors = if (darkTheme) CustomColorsDark else CustomColorsLight
    val taskGroupColors = MaterialTheme.taskGroupColors
    val textSelectionColors = TextSelectionColors(
        handleColor = if (darkTheme) darkScheme.onSurface else lightScheme.onSurface,
        backgroundColor = if (darkTheme) darkScheme.surface else lightScheme.surface
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography
    ) {
        CompositionLocalProvider(
            LocalCustomColors provides customColors,
            LocalTaskGroupColors provides taskGroupColors,
            LocalTextSelectionColors provides textSelectionColors,
            content = content
        )
    }
}
