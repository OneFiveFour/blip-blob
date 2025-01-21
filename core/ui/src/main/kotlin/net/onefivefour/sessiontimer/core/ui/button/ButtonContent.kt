package net.onefivefour.sessiontimer.core.ui.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun ButtonContent(
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