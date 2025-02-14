package net.onefivefour.sessiontimer.core.ui.duration

import java.util.Locale
import kotlin.time.Duration

internal fun Duration.toSixDigitsString() = toComponents { h, m, s, _ ->
    String.format(Locale.getDefault(), "%02d%02d%02d", h, m, s)
}

internal fun String.toDuration() : Duration {
    val newHours = this
        .take(2)

    val newMinutes = this
        .dropLast(2)
        .takeLast(2)

    val newSeconds = this
        .takeLast(2)

    val newTotalSeconds = newHours.toIntOrZero() * 3600 +
            newMinutes.toIntOrZero() * 60 +
            newSeconds.toIntOrZero()

    return Duration.parse("${newTotalSeconds}s")
}

internal fun String.toIntOrZero(): Int {
    return try {
        toInt()
    } catch (e: NumberFormatException) {
        0
    }
}
