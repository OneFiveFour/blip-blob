package net.onefivefour.sessiontimer.core.common.extensions

import kotlin.time.Duration

fun String.toIntOrZero(): Int {
    return try {
        toInt()
    } catch (e: NumberFormatException) {
        0
    }
}

fun String.toDuration() : Duration {
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