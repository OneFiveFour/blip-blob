package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
internal fun PlayModeButton(
    @DrawableRes iconRes: Int,
    @StringRes contentDescription: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = selectionBorder(isSelected)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(TILE_SIZE_DP),
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(id = contentDescription),
            tint = MaterialTheme.colorScheme.onSurface
        )
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