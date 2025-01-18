
package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.components.glow.drawGlowingSides
import net.onefivefour.sessiontimer.core.ui.components.dragger.Dragger
import net.onefivefour.sessiontimer.core.ui.components.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.feature.sessioneditor.R
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask

internal val TASK_ITEM_HEIGHT = 64.dp

@Composable
internal fun TaskItem(
    modifier: Modifier = Modifier,
    uiTask: UiTask,
    onTaskTitleChanged: (String) -> Unit
) {

    val glowColor = MaterialTheme.customColors.surfaceGlow

    val backgroundColor = MaterialTheme.colorScheme.surface

    Row(
        modifier = modifier
            .height(TASK_ITEM_HEIGHT)
            .drawWithContent {
                drawGlowingSides(
                    glowColor = glowColor,
                    backgroundColor = backgroundColor
                )
            }
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Dragger()

        Spacer(modifier = Modifier.width(6.dp))

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
            textStyle = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.onSurface)
        )

        Icon(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.ic_stopwatch),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = stringResource(id = R.string.set_duration)
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
                uiTask = uiTask1,
                onTaskTitleChanged = { }
            )
        }
    }
}
