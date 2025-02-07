package net.onefivefour.sessiontimer.core.ui.sqarebutton

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.R

@Composable
fun SquareButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
    contentDescription: String,
    @ColorInt backgroundColor: Color = MaterialTheme.colorScheme.surface,
    size: Dp = 64.dp,
    cornerRadius: Dp = 8.dp,
    onClick: () -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(size)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = SquareButtonIndicationNodeFactory(
                    backgroundColor = backgroundColor,
                    glowColor = MaterialTheme.customColors.surfaceGlow,
                    cornerRadius = cornerRadius
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        if (iconRes != null) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EditButtonPreview() {
    SessionTimerTheme {
        Surface {
            SquareButton(
                iconRes = R.drawable.ic_edit,
                contentDescription = "Edit",
                onClick = { }
            )
        }
    }
}