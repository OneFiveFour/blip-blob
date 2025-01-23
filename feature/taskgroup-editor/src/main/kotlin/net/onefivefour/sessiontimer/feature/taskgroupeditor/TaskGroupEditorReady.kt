package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import net.onefivefour.sessiontimer.core.ui.labelline.LabelLine
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TaskGroupEditorReady(
    taskGroup: UiTaskGroup,
    onAction: (TaskGroupEditorAction) -> Unit,
    goBack: () -> Unit
) {
    Column {

        ScreenTitle(titleRes = R.string.edit_task_group)

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clearFocusOnKeyboardDismiss(),
            value = TextFieldValue(
                text = taskGroup.title,
                selection = TextRange(taskGroup.title.length)
            ),
            onValueChange = { newText -> onAction(TaskGroupEditorAction.SetTitle(newText.text)) },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium
                .copy(color = MaterialTheme.colorScheme.onSurface),
        )

        LabelLine(labelRes = R.string.title)

        val isPreview = LocalInspectionMode.current
        val animatedVisibility = !WindowInsets.isImeVisible || isPreview

        AnimatedVisibility(animatedVisibility) {
            Column {
                ColorGrid(
                    colors = MaterialTheme.taskGroupColors.getAll(),
                    selectedColor = taskGroup.color,
                    columnsCount = 6,
                    onColorClick = { color -> onAction(TaskGroupEditorAction.SetColor(color)) }
                )

                LabelLine(labelRes = R.string.color)

                PlayModeSelection(
                    playMode = taskGroup.playMode,
                    numberOfRandomTasks = taskGroup.numberOfRandomTasks,
                    numberOfTasks = taskGroup.tasks.size,
                    onPlayModeChanged = { playMode, numberOfRandomTasks ->
                        onAction(TaskGroupEditorAction.SetPlayMode(playMode, numberOfRandomTasks))
                    }
                )

                LabelLine(labelRes = R.string.play_mode)

                SquareButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    iconRes = net.onefivefour.sessiontimer.core.ui.R.drawable.ic_save,
                    contentDescriptionRes = R.string.save,
                    onClick = goBack
                )
            }
        }
    }
}



@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupEditorReadyPreview() {
    SessionTimerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TaskGroupEditor(
                uiState = UiState.Ready(taskGroup = uiTaskGroup()),
                onAction = { },
                goBack = { }
            )
        }
    }
}