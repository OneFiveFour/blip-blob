package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import net.onefivefour.sessiontimer.core.ui.duration.DurationInput
import net.onefivefour.sessiontimer.core.ui.labelline.LabelLine
import net.onefivefour.sessiontimer.core.ui.labelline.LabelLineTextField
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.core.ui.utils.toPx
import net.onefivefour.sessiontimer.core.ui.R as UiR

internal val TILE_SIZE = 60.dp

@Composable
internal fun TaskGroupEditorReady(
    taskGroup: UiTaskGroup,
    onAction: (TaskGroupEditorAction) -> Unit,
    goBack: () -> Unit,
) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
    ) {

        // available width minus 24.dp horizontal padding
        val totalWidthPx = constraints.maxWidth
        val tileSizePx = TILE_SIZE.toPx()

        // calculaate the maximum column count based on available width
        val columnCount = (totalWidthPx / tileSizePx).toInt()

        // Calculate horizontal gap based on available width
        val horizontalGapPx = (totalWidthPx - (columnCount * tileSizePx)) / (columnCount - 1)
        val gapSize = with(LocalDensity.current) { horizontalGapPx.toDp() }

        val scrollState = rememberScrollState()
        
        ScreenTitle(
            modifier = Modifier.align(Alignment.TopCenter),
            titleRes = R.string.edit_task_group
        )

        Column(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {

            LabelLineTextField(
                labelRes = R.string.title,
                text = taskGroup.title,
                onValueChange = { newText -> onAction(TaskGroupEditorAction.SetTitle(newText.text)) }
            )

            Column {
                ColorGrid(
                    colors = MaterialTheme.taskGroupColors.getAll(),
                    selectedColor = taskGroup.color,
                    columnsCount = 5,
                    gapSize = gapSize,
                    onColorClick = { color -> onAction(TaskGroupEditorAction.SetColor(color)) }
                )

                LabelLine(
                    modifier = Modifier.padding(top = 4.dp),
                    labelRes = R.string.color
                )
            }

            Column {
                PlayModeSelection(
                    playMode = taskGroup.playMode,
                    numberOfRandomTasks = taskGroup.numberOfRandomTasks,
                    numberOfTasks = taskGroup.tasks.size,
                    gapSize = gapSize,
                    onPlayModeChanged = { playMode, numberOfRandomTasks ->
                        onAction(
                            TaskGroupEditorAction.SetPlayMode(
                                playMode,
                                numberOfRandomTasks
                            )
                        )
                    }
                )

                LabelLine(
                    modifier = Modifier.padding(top = 4.dp),
                    labelRes = R.string.play_mode
                )
            }

            Column {

                DurationInput(
                    hours = taskGroup.defaultTaskDuration.hours,
                    minutes = taskGroup.defaultTaskDuration.minutes,
                    seconds = taskGroup.defaultTaskDuration.seconds,
                    onNumberEntered = { currentString, newDuration ->
                        onAction(TaskGroupEditorAction.OnDurationNumberEntered(
                            currentString = currentString,
                            numberEntered = newDuration
                        ))
                    }
                )

                LabelLine(
                    modifier = Modifier.padding(top = 4.dp),
                    labelRes = R.string.default_task_duration
                )
            }
        }

        SquareButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            iconRes = UiR.drawable.ic_save,
            contentDescriptionRes = R.string.save,
            onClick = goBack
        )
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupEditorReadyPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupEditor(
                uiState = UiState.Ready(taskGroup = uiTaskGroup()),
                onAction = { },
                goBack = { }
            )
        }
    }
}