package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import net.onefivefour.sessiontimer.core.ui.components.button.PrimaryButton
import net.onefivefour.sessiontimer.core.ui.components.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.core.ui.R as UiR

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TaskGroupEditor(
    uiState: UiState,
    onTitleChanged: (String) -> Unit,
    onColorChanged: (Color) -> Unit,
    onPlayModeChanged: (PlayMode, Int) -> Unit,
    onSave: () -> Unit,
) {
    when (uiState) {
        UiState.Initial -> {
            Text(text = "TaskGroup Editor")
            return
        }

        is UiState.Ready -> {
            checkNotNull(uiState.taskGroup)
        }
    }

    val taskGroup = uiState.taskGroup

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                start = 48.dp,
                end = 48.dp,
                bottom = 16.dp
            )
    ) {

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.edit_task_group),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clearFocusOnKeyboardDismiss(),
            value = TextFieldValue(
                text = taskGroup.title,
                selection = TextRange(taskGroup.title.length)
            ),
            onValueChange = { newText -> onTitleChanged(newText.text) },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )

        LabelLine(labelRes = R.string.title)

        AnimatedVisibility(!WindowInsets.isImeVisible) {
            Column {
                ColorGrid(
                    colors = MaterialTheme.taskGroupColors.getAll(),
                    selectedColor = taskGroup.color,
                    columnsCount = 6,
                    onColorClick = { color -> onColorChanged(color) }
                )

                LabelLine(
                    modifier = Modifier.padding(top = 8.dp),
                    labelRes = R.string.color
                )

                PlayModeSelection(
                    playMode = taskGroup.playMode,
                    numberOfRandomTasks = taskGroup.numberOfRandomTasks,
                    numberOfTasks = taskGroup.tasks.size,
                    onPlayModeChanged = onPlayModeChanged
                )

                LabelLine(
                    modifier = Modifier.padding(top = 8.dp),
                    labelRes = R.string.play_mode
                )

                PrimaryButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.save),
                    iconRes = UiR.drawable.ic_save,
                    contentDescription = stringResource(R.string.save),
                    onClick = onSave
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupEditorPreview() {
    SessionTimerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TaskGroupEditor(
                uiState = UiState.Ready(taskGroup = uiTaskGroup()),
                onTitleChanged = { },
                onColorChanged = { },
                onPlayModeChanged = { _, _ -> },
                onSave = { }
            )
        }
    }
}
