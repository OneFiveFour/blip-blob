package net.onefivefour.sessiontimer.core.common.extensions

import java.util.Locale
import kotlin.time.Duration

fun Duration.toSixDigitsString() = toComponents { h, m, s, _ ->
    String.format(Locale.getDefault(), "%02d%02d%02d", h, m, s)
}

