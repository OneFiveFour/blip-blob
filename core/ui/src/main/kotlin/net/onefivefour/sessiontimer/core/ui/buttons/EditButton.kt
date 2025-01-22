package net.onefivefour.sessiontimer.core.ui.buttons

import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.customColors
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.glow.drawGlowingSides

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    val backgroundColor = MaterialTheme.colorScheme.surface
    val glowColor = MaterialTheme.customColors.surfaceGlow.copy(alpha = 0.3f)

    IconButton(
        modifier = modifier.drawWithContent {
            drawGlowingSides(
                glowColor = glowColor,
                backgroundColor = backgroundColor
            )
        },
        onClick = onClick
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = stringResource(R.string.edit)
        )

    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EditButtonPreview() {
    SessionTimerTheme {
        Surface {
            EditButton {  }
        }
    }
}