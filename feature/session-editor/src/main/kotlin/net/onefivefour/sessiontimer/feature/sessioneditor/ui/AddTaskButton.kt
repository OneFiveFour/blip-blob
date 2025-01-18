package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.glow.drawGlowingSides

@Composable
internal fun AddTaskButton(
    onNewTask: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val glowColor = MaterialTheme.customColors.surfaceGlow

    Box(
        modifier = Modifier
            .padding(start = 16.dp)
            .size(TASK_ITEM_HEIGHT)
            .drawWithContent {
                drawGlowingSides(
                    glowColor = glowColor,
                    backgroundColor = backgroundColor
                )
            }
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onNewTask() }
                ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = stringResource(net.onefivefour.sessiontimer.feature.sessioneditor.R.string.add_task),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AddTaskButtonPreview() {
    SessionTimerTheme {
        Surface {
            AddTaskButton { }
        }
    }
}