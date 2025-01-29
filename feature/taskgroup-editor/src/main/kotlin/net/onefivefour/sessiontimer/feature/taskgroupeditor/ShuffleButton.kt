package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme

@Composable
internal fun ShuffleButton(
    @DrawableRes iconRes: Int,
    @StringRes contentDescriptionRes: Int,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(enabled = isEnabled, onClick = onClick)
            .padding(10.dp),
        painter = painterResource(id = iconRes),
        contentDescription = stringResource(contentDescriptionRes),
        tint = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.3f
        )
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ShuffleButtonPreview() {
    SessionTimerTheme {
        Surface {
            ShuffleButton(
                iconRes = R.drawable.ic_plus,
                contentDescriptionRes = R.string.add_random_task,
                isEnabled = true,
                onClick = { }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ShuffleButtonDisabledPreview() {
    SessionTimerTheme {
        Surface {
            ShuffleButton(
                iconRes = R.drawable.ic_plus,
                contentDescriptionRes = R.string.add_random_task,
                isEnabled = false,
                onClick = { }
            )
        }
    }
}