package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.feature.sessioneditor.R
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction
import net.onefivefour.sessiontimer.core.ui.R as UiR

@Composable
internal fun TaskGroupPage(
    modifier: Modifier = Modifier,
    uiTaskGroup: UiTaskGroup,
    onOpenTaskGroupEditor: (Long) -> Unit,
    onAction: (SessionEditorAction) -> Unit,
) {

    val lazyListState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        TaskList(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            lazyListState = lazyListState,
            uiTaskGroup = uiTaskGroup,
            onAction = onAction
        )

        AddTaskItem(
            modifier = Modifier.padding(horizontal = 12.dp),
            uiTaskGroup = uiTaskGroup,
            onAction = onAction
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(uiTaskGroup.color, RoundedCornerShape(8.dp))
                .padding(start = 12.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = uiTaskGroup.title,
                style = MaterialTheme.typography.titleMedium,
                color = uiTaskGroup.onColor
            )

            SquareButton(
                iconRes = UiR.drawable.ic_edit,
                contentDescription = stringResource(R.string.edit_task_group),
            ) {
                onOpenTaskGroupEditor(uiTaskGroup.id)
            }
        }
    }

}

@Preview
@Composable
private fun TaskGroupPagePreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupPage(
                uiTaskGroup = fakeUiTaskGroup(),
                onOpenTaskGroupEditor = {},
                onAction = {}
            )
        }
    }
}