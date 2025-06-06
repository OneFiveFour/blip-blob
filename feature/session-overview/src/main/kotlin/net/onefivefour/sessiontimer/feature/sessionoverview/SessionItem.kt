package net.onefivefour.sessiontimer.feature.sessionoverview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R as UiR
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton

@Composable
internal fun SessionItem(
    modifier: Modifier = Modifier,
    uiSession: UiSession,
    onStartSession: (Long) -> Unit,
    onEditSession: (Long) -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DragHandler()

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier
                .weight(1f)
                .clickable { onStartSession(uiSession.id) },
            color = MaterialTheme.colorScheme.onBackground,
            text = uiSession.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        SquareButton(
            iconRes = UiR.drawable.ic_edit,
            contentDescription = stringResource(R.string.edit_session),
            onClick = { onEditSession(uiSession.id) }
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionItemPreview() {
    SessionTimerTheme {
        Surface {
            SessionItem(
                uiSession = UiSession(
                    id = 1,
                    title = "A Session",
                    sortOrder = 1,
                    createdAt = Clock.System.now()
                ),
                onEditSession = {},
                onStartSession = {}
            )
        }
    }
}
