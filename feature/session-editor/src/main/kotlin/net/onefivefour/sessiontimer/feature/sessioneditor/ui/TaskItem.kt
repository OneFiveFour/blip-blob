
package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask

@Composable
internal fun TaskItem(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    onTaskTitleChanged: (String) -> Unit
) {

    Row(
        modifier = modifier
            .height(64.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {

        DragHandler()

        Spacer(Modifier.width(12.dp))

        // TODO change to textfieldstate
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .clearFocusOnKeyboardDismiss(),
            value = TextFieldValue(
                text = uiTask.title,
                selection = TextRange(uiTask.title.length)
            ),
            onValueChange = { newText -> onTaskTitleChanged(newText.text) },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            singleLine = true,
            textStyle = MaterialTheme.typography.titleSmall
                .copy(color = MaterialTheme.colorScheme.onSurface)
        )

        Text(
            text = uiTask.duration.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreview() {
    SessionTimerTheme {
        Surface {
            TaskItem(
                uiTask = uiTask3,
                onTaskTitleChanged = { }
            )
        }
    }
}
