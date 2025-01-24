package net.onefivefour.sessiontimer.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp

@Composable
fun TextStyle.topToAscentDp(): Dp {

    val textMeasurer = rememberTextMeasurer()

    val measurements = remember(textMeasurer, this) {
        textMeasurer.measure(
            text = "H",
            style = this
        )
    }

    val result = measurements.size.height - measurements.firstBaseline
    return with(LocalDensity.current) {
        result.toDp()
    }
}