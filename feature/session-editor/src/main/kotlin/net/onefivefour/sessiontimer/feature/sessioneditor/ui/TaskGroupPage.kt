package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.feature.sessioneditor.R
import net.onefivefour.sessiontimer.core.ui.R as UiR
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup

@Composable
internal fun TaskGroupPage(
    modifier: Modifier = Modifier,
    uiTaskGroup: UiTaskGroup,
    onOpenTaskGroupEditor: (Long) -> Unit,
) {

    Box(
        modifier = modifier
            .background(uiTaskGroup.color, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {

        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = uiTaskGroup.title,
            style = MaterialTheme.typography.titleMedium,
            color = uiTaskGroup.onColor
        )

        SquareButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            iconRes = UiR.drawable.ic_edit,
            contentDescription = stringResource(R.string.edit_task_group),
        ) {
            onOpenTaskGroupEditor(uiTaskGroup.id)
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
                onOpenTaskGroupEditor = {}
            )
        }
    }
}