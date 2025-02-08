
package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.zIndex
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

@Composable
internal fun TaskItem(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    onAction: (SessionEditorAction) -> Unit
) {

    Row(
        modifier = modifier
            .height(64.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {

        DragHandler()

        Spacer(Modifier.width(12.dp))

        val textFieldState = rememberTextFieldState(
            initialText = uiTask.title,
            initialSelection = TextRange(uiTask.title.length)
        )

        Text(
            modifier = Modifier.weight(1f),
            text = uiTask.title,
            style = MaterialTheme.typography.titleSmall
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )

//        BasicTextField(
//            modifier = Modifier.clearFocusOnKeyboardDismiss(),
//            inputTransformation = {
//                val newTitle = asCharSequence().toString()
//                onAction(SessionEditorAction.SetTaskTitle(uiTask.id, newTitle))
//            },
//            state = textFieldState,
//            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
//            lineLimits = TextFieldLineLimits.SingleLine,
//            textStyle = MaterialTheme.typography.titleSmall
//                .copy(color = MaterialTheme.colorScheme.onSurface),
//        )

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
                onAction = { }
            )
        }
    }
}
