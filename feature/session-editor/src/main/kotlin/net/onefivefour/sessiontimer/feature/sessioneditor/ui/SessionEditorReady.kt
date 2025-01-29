package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.label.LabeledSection
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.utils.topToAscentDp
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction
import sh.calvin.reorderable.ReorderableColumn

@Composable
internal fun SessionEditorReady(
    uiSession: UiSession,
    onAction: (SessionEditorAction) -> Unit,
    openTaskGroupEditor: (Long) -> Unit,
) {
    val haptic = rememberReorderHapticFeedback()

    var taskGroupList by remember(uiSession.taskGroups) { mutableStateOf(uiSession.taskGroups) }

    var collapsedTaskGroupsIds by remember { mutableStateOf(setOf<Long>()) }

    Column(modifier = Modifier.fillMaxWidth()) {

        ScreenTitle(titleRes = R.string.edit_session)

        LabeledSection(R.string.title) {
            val textStyle = MaterialTheme.typography.titleMedium
            val offset = textStyle.topToAscentDp() - 4.dp

            // TODO replace by new basictextfield
            BasicTextField(
                modifier = Modifier
                    .zIndex(1f)
                    .offset(y = offset)
                    .fillMaxWidth()
                    .clearFocusOnKeyboardDismiss(),
                value = TextFieldValue(
                    text = uiSession.title,
                    selection = TextRange(uiSession.title.length)
                ),
                onValueChange = { newText -> onAction(SessionEditorAction.SetSessionTitle(newText.text)) },
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium
                    .copy(color = MaterialTheme.colorScheme.onSurface),
            )
        }

        ReorderableColumn(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            list = taskGroupList,
            onSettle = { fromIndex, toIndex ->
                taskGroupList = taskGroupList.toMutableList().apply {
                    add(toIndex, removeAt(fromIndex))
                }
                val taskGroupIds = taskGroupList.map { it.id }
                onAction(SessionEditorAction.UpdateTaskGroupSortOrders(taskGroupIds))
            },
            onMove = {
                haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
            },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) { _, taskGroup, _ ->
            key(taskGroup.id) {
                val interactionSource = remember { MutableInteractionSource() }

                var isDragging by remember { mutableStateOf(false) }

                TaskGroupItem(
                    modifier = Modifier
                        .longPressDraggableHandle(
                            onDragStarted = {
                                haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                isDragging = true
                            },
                            onDragStopped = {
                                haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                isDragging = false
                            },
                            interactionSource = interactionSource
                        ),
                    uiTaskGroup = taskGroup,
                    isCollapsed = collapsedTaskGroupsIds.contains(taskGroup.id),
                    isDragging = isDragging,
                    onCollapseChanged = { isCollapsed ->
                        collapsedTaskGroupsIds = when {
                            isCollapsed -> collapsedTaskGroupsIds + taskGroup.id
                            else -> collapsedTaskGroupsIds - taskGroup.id
                        }
                    },
                    openTaskGroupEditor = { openTaskGroupEditor(taskGroup.id) },
                    onAction = onAction,
                )
            }
        }

        SquareButton(
            iconRes = R.drawable.ic_add,
            contentDescriptionRes = R.string.new_task_group,
            onClick = { onAction(SessionEditorAction.CreateTaskGroup) }
        )
    }
}

@Preview
@Composable
private fun SessionEditorReadyPreview() {
    SessionTimerTheme {
        Surface {
            SessionEditorReady(
                uiSession = UiSession(
                    title = "Session Title",
                    taskGroups = listOf(
                        fakeUiTaskGroup()
                    )
                ),
                onAction = { },
                openTaskGroupEditor = { }
            )
        }
    }
}
