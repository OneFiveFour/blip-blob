package net.onefivefour.sessiontimer.feature.sessionoverview

import android.content.res.Configuration.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButtonIndicationNodeFactory
import net.onefivefour.sessiontimer.core.ui.draghandler.DragHandler
import net.onefivefour.sessiontimer.core.ui.R as UiR

@Composable
internal fun SessionItem(
    modifier: Modifier = Modifier,
    uiSession: UiSession,
    onStartSession: (Long) -> Unit,
    onEditSession: (Long) -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = SquareButtonIndicationNodeFactory(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    glowColor = MaterialTheme.customColors.surfaceGlow
                )
            ) { onStartSession(uiSession.id) }
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DragHandler()

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            text = uiSession.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onEditSession(uiSession.id) }
                .padding(4.dp),
            painter = painterResource(id = UiR.drawable.ic_edit),
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = stringResource(id = R.string.edit_session)
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
