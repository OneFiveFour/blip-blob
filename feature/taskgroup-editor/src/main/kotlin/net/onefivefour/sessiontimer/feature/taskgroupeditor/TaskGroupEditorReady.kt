package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors
import net.onefivefour.sessiontimer.core.ui.duration.DurationInput
import net.onefivefour.sessiontimer.core.ui.label.LabeledSection
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.core.ui.utils.topToAscentDp
import net.onefivefour.sessiontimer.core.ui.R as UiR

internal val TILE_SIZE_DP = 60.dp

internal val MINIMAL_GAP_SIZE_DP = 8.dp

@Composable
internal fun TaskGroupEditorReady(
    uiTaskGroup: UiTaskGroup,
    onAction: (TaskGroupEditorAction) -> Unit,
    goBack: () -> Unit,
) {

    val scrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
    ) {

        val density = LocalDensity.current

        val (columnCount, gapSizeDp) = remember(constraints.maxWidth, density) {
            val totalWidthDp = with(density) { constraints.maxWidth.toDp() }
            val colCount = (totalWidthDp / (MINIMAL_GAP_SIZE_DP + TILE_SIZE_DP)).toInt()
            val gapSize = when {
                colCount > 1 -> (totalWidthDp - (colCount * TILE_SIZE_DP)) / (colCount - 1)
                else -> 0.dp
            }
            colCount to gapSize
        }

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

            LabeledSection(R.string.title) {
                val textStyle = MaterialTheme.typography.titleMedium
                val offset = textStyle.topToAscentDp() - 4.dp

                BasicTextField(
                    modifier = Modifier
                        .zIndex(1f)
                        .offset(y = offset)
                        .fillMaxWidth()
                        .clearFocusOnKeyboardDismiss(),
                    state = uiTaskGroup.title,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle = MaterialTheme.typography.titleMedium
                        .copy(color = MaterialTheme.colorScheme.onSurface),
                )
            }

            LabeledSection(labelRes = R.string.color) {
                ColorGrid(
                    colors = MaterialTheme.taskGroupColors.getAll(),
                    selectedColor = uiTaskGroup.color,
                    columnsCount = columnCount,
                    gapSize = gapSizeDp,
                    onColorClick = { color -> onAction(TaskGroupEditorAction.SetColor(color)) }
                )
            }

            LabeledSection(labelRes = R.string.play_mode) {
                PlayModeSelection(
                    playMode = uiTaskGroup.playMode,
                    numberOfRandomTasks = uiTaskGroup.numberOfRandomTasks,
                    numberOfTasks = uiTaskGroup.tasks.size,
                    gapSize = gapSizeDp,
                    onPlayModeChanged = { playMode, numberOfRandomTasks ->
                        onAction(
                            TaskGroupEditorAction.SetPlayMode(
                                playMode,
                                numberOfRandomTasks
                            )
                        )
                    }
                )
            }


            LabeledSection(labelRes = R.string.default_task_duration)  {
                DurationInput(
                    hours = uiTaskGroup.defaultTaskDuration.hours,
                    minutes = uiTaskGroup.defaultTaskDuration.minutes,
                    seconds = uiTaskGroup.defaultTaskDuration.seconds,
                    onNumberEntered = { currentString, newDuration ->
                        onAction(
                            TaskGroupEditorAction.OnDurationNumberEntered(
                                currentString = currentString,
                                numberEntered = newDuration
                            )
                        )
                    }
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