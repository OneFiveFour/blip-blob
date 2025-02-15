package net.onefivefour.sessiontimer.core.ui.label

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.utils.topToAscentDp

@Composable
internal fun LabelLine(modifier: Modifier = Modifier, @StringRes labelRes: Int) {
    val labelTextStyle = MaterialTheme.typography.labelSmall

    val textMeasurer = rememberTextMeasurer()
    val label = stringResource(labelRes).uppercase()

    val textMeasurements = remember(textMeasurer) {
        textMeasurer.measure(
            text = label,
            style = labelTextStyle
        )
    }

    val offset = labelTextStyle.topToAscentDp() - 6.dp

    val lineThickness = 2.dp

    Layout(
        modifier = modifier,
        content = {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(lineThickness)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Text(
                text = label,
                style = labelTextStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    ) { measurables, constraints ->

        val linePlaceable = measurables[0].measure(
            constraints.copy(
                minHeight = lineThickness.roundToPx(),
                maxHeight = lineThickness.roundToPx()
            )
        )
        val textPlaceable = measurables[1].measure(
            constraints.copy(
                minWidth = textMeasurements.size.width,
                maxWidth = textMeasurements.size.width
            )
        )

        // height of linePlacement can be ignored, because the line is covered by the text
        val height = (textPlaceable.height - offset.roundToPx())
            .coerceAtLeast(0)

        layout(constraints.maxWidth, height) {
            linePlaceable.placeRelative(0, 0)
            textPlaceable.placeRelative(0, -offset.roundToPx())
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LabelLinePreview() {
    SessionTimerTheme {
        Surface {
            LabelLine(labelRes = R.string.new_task)
        }
    }
}
