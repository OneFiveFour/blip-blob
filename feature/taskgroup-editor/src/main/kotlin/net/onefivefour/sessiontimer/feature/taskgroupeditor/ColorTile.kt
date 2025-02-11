package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors

@Composable
internal fun ColorTile(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    val animatedCornerRadius by animateDpAsState(
        targetValue = if (isSelected) 14.dp else 4.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val targetBorderColor = when {
        isSelected -> color.copy(
            red = color.red / 2,
            green = color.green / 2,
            blue = color.blue / 2
        )
        else -> color
    }

    val animatedBorderColor by animateColorAsState(
        targetValue = targetBorderColor,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .size(TILE_SIZE_DP)
            .background(
                color = color,
                shape = RoundedCornerShape(animatedCornerRadius)
            )
            .border(
                shape = RoundedCornerShape(animatedCornerRadius),
                width = if (isSelected) 2.dp else 0.dp,
                color = animatedBorderColor
            )
            .clickable { onClick() }
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ColorTilePreview() {
    SessionTimerTheme {
        Surface {
            ColorTile(
                color = MaterialTheme.taskGroupColors.color12.first,
                isSelected = false,
                onClick = {}
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ColorTileSelectedPreview() {
    SessionTimerTheme {
        Surface {
            ColorTile(
                color = MaterialTheme.taskGroupColors.color10.first,
                isSelected = true,
                onClick = {}
            )
        }
    }
}