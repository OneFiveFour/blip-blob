package net.onefivefour.sessiontimer.core.ui.labelline

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.core.ui.utils.topToAscentDp

@Composable
fun LabelLineTextField(
    modifier: Modifier = Modifier,
    @StringRes labelRes: Int,
    text: String,
    onValueChange: (TextFieldValue) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        val textStyle = MaterialTheme.typography.titleMedium
        val offset = textStyle.topToAscentDp() - 4.dp

        BasicTextField(
            modifier = Modifier
                .zIndex(1f)
                .offset(y = offset)
                .fillMaxWidth()
                .clearFocusOnKeyboardDismiss(),
            value = TextFieldValue(
                text = text,
                selection = TextRange(text.length)
            ),
            onValueChange = onValueChange,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )

        LabelLine(labelRes = labelRes)
    }
}

@Preview
@Composable
private fun TextLabelLinePreview() {
    SessionTimerTheme {
        Surface {
            LabelLineTextField(
                labelRes = R.string.play_mode_sequence,
                text = stringResource(R.string.new_task_group),
                onValueChange = { }
            )
        }
    }
}