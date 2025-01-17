package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.*
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.components.glow.drawGlowingSides
import net.onefivefour.sessiontimer.core.ui.components.dragger.Dragger
import net.onefivefour.sessiontimer.feature.sessioneditor.R
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask

internal val TASK_ITEM_HEIGHT = 64.dp

@Composable
internal fun TaskItem(
    modifier: Modifier = Modifier,
    uiTask: UiTask
) {

    val glowColor = MaterialTheme.customColors.surfaceGlow
    val backgroundColor = MaterialTheme.colorScheme.surface

    Row(
        modifier = modifier
            .height(TASK_ITEM_HEIGHT)
            .drawWithContent {
                drawGlowingSides(
                    glowColor = glowColor,
                    backgroundColor = backgroundColor
                )
            }
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Dragger()

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = uiTask.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.ic_stopwatch),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = stringResource(id = R.string.set_duration)
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreview() {
    SessionTimerTheme {
        Surface {
            TaskItem(
                uiTask = uiTask1
            )
        }
    }
}
