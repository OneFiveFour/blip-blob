package net.onefivefour.sessiontimer.feature.sessionoverview

import android.content.res.Configuration.*
import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.components.button.GlowingButtonIndicationNodeFactory
import net.onefivefour.sessiontimer.core.ui.components.dragger.Dragger

@Composable
internal fun SessionItem(
    modifier: Modifier = Modifier,
    session: UiSession,
    onStartSession: (Long) -> Unit,
    onSetSessionTitle: (UiSession, String) -> Unit,
    onEditSession: (Long) -> Unit,
    onDeleteSession: (Long) -> Unit
) {
    val cornerRadius = 8.dp

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = GlowingButtonIndicationNodeFactory(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    glowColor = MaterialTheme.customColors.surfaceGlow
                )
            ) { onStartSession(session.id) }
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Dragger()

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground,
            text = session.title,
            style = MaterialTheme.typography.titleMedium
        )

        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .clickable { onEditSession(session.id) }
                .padding(4.dp),
            painter = painterResource(id = net.onefivefour.sessiontimer.core.ui.R.drawable.ic_edit),
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = stringResource(id = R.string.edit_session)
        )

        // avoid unused var message
        Log.d("+++", "remove me: $onDeleteSession, $onSetSessionTitle")
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionItemPreview() {
    SessionTimerTheme {
        Surface {
            SessionItem(
                session = UiSession(
                    id = 1,
                    title = "A Session",
                    sortOrder = 1
                ),
                onEditSession = {},
                onStartSession = {},
                onDeleteSession = {},
                onSetSessionTitle = { _, _ -> }
            )
        }
    }
}
