package net.onefivefour.sessiontimer.core.common.utils

fun String.toIntOrZero(): Int {
    return try {
        toInt()
    } catch (e: NumberFormatException) {
        0
    }
}