package net.onefivefour.sessiontimer.core.ui.button

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.R

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int? = null,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = GlowingButtonIndicationNodeFactory(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    glowColor = MaterialTheme.customColors.surfaceGlow
                )
            )
            .padding(
                vertical = 18.dp,
                horizontal = 32.dp
            )
    ) {
        ButtonContent(
            iconRes = iconRes,
            text = text,
            contentDescription = contentDescription
        )
    }
}

@Composable
private fun ButtonContent(
    iconRes: Int?,
    text: String,
    alpha: Float = 1f,
    contentDescription: String? = null
) {
    val contentColor = MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier.alpha(alpha = alpha),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = contentDescription,
                tint = contentColor
            )
            Spacer(modifier = Modifier.width(14.dp))
        }

        Text(
            style = MaterialTheme.typography.labelLarge,
            text = text,
            color = contentColor
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryButtonPreview() {
    SessionTimerTheme {
        Surface {
            PrimaryButton(
                text = "New Session",
                iconRes = R.drawable.ic_add
            ) {}
        }
    }
}
