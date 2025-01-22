package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.dragger.Dragger
import net.onefivefour.sessiontimer.feature.sessioneditor.R

@Composable
internal fun TaskGroupHeader(
    taskGroupTitle: String,
    onCollapseChanged: (Boolean) -> Unit,
    isCollapsed: Boolean,
) {

    Row(
        modifier = Modifier.height(TASK_GROUP_HEADER_HEIGHT),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Dragger()

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            text = taskGroupTitle,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val collapseIconRes = when {
            isCollapsed -> R.drawable.ic_collapse_down
            else -> R.drawable.ic_collapse_up
        }

        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onCollapseChanged(!isCollapsed) }
                .padding(8.dp),
            painter = painterResource(id = collapseIconRes),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = stringResource(id = R.string.collapse)
        )

        Spacer(modifier = Modifier.width(6.dp))
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupSummaryPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupHeader(
                taskGroupTitle = "Task Group Title",
                onCollapseChanged = { },
                isCollapsed = false
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupSummaryCollapsedPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupHeader(
                taskGroupTitle = "Very Very Long Title that would need several lines of text",
                onCollapseChanged = { },
                isCollapsed = true
            )
        }
    }
}