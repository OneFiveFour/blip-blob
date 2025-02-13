package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme

@Composable
internal fun PlayModeButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = selectionBorder(isSelected)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content?.invoke()
    }
}

@Composable
private fun selectionBorder(isSelected: Boolean): Modifier {
    val cornerShape = RoundedCornerShape(6.dp)
    val borderColor = when {
        isSelected -> MaterialTheme.colorScheme.onSurface
        else -> Color.Transparent
    }

    return Modifier
        .clip(cornerShape)
        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
        .border(
            width = 2.dp,
            color = borderColor,
            shape = cornerShape
        )
}

@Preview
@Composable
private fun PlayModeButtonPreview() {
    SessionTimerTheme {
        Surface {
            PlayModeButton(
                isSelected = false,
                onClick = {  },
                content = {  }
            )
        }
    }
}