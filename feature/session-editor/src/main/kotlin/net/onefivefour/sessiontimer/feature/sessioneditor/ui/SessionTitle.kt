package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.label.LabeledSection
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.core.ui.utils.topToAscentDp
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

@Composable
internal fun SessionTitle(
    uiSession: UiSession,
    onAction: (SessionEditorAction) -> Unit,
) {
    LabeledSection(
        modifier = Modifier.padding(horizontal = 24.dp),
        labelRes = R.string.title,
    ) {
        val textStyle = MaterialTheme.typography.titleMedium
        val offset = textStyle.topToAscentDp() - 4.dp

        val textFieldState = rememberTextFieldState()

        LaunchedEffect(uiSession.title) {
            textFieldState.edit {
                replace(
                    start = 0,
                    end = length,
                    text = uiSession.title
                )
            }
        }

        BasicTextField(
            modifier = Modifier
                .zIndex(1f)
                .offset(y = offset)
                .fillMaxWidth()
                .clearFocusOnKeyboardDismiss(),
            inputTransformation = {
                val newTitle = asCharSequence().toString()
                onAction(SessionEditorAction.SetSessionTitle(newTitle))
            },
            state = textFieldState,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = MaterialTheme.typography.titleMedium
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionTitlePreview() {
    SessionTimerTheme {
        Surface {
            SessionTitle(
                uiSession = UiSession(
                    title = "Session Title",
                    taskGroups = emptyList()
                ),
                onAction = { }
            )
        }
    }
}