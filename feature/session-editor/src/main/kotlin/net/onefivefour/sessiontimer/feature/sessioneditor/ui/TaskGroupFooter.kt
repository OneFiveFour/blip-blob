package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTaskGroup

@Composable
internal fun TaskGroupFooter(
    modifier: Modifier = Modifier,
    uiTaskGroup: UiTaskGroup,
    onEditTaskGroup: () -> Unit,
) {

    val playModeIconRes = when (uiTaskGroup.playMode) {
        PlayMode.SEQUENCE -> R.drawable.ic_play_mode_sequence
        else -> R.drawable.ic_play_mode_shuffle
    }

    val playModeDescriptionRes = when (uiTaskGroup.playMode) {
        PlayMode.SEQUENCE -> R.string.play_mode_sequence
        else -> R.string.play_mode_shuffle
    }

    Row(
        modifier = modifier
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(29.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(uiTaskGroup.color)
        )

        Icon(
            painter = painterResource(playModeIconRes),
            contentDescription = stringResource(playModeDescriptionRes),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        SquareButton(
            iconRes = R.drawable.ic_edit,
            contentDescription = stringResource(R.string.edit),
            onClick = onEditTaskGroup
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskGroupFooterPreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupFooter(
                uiTaskGroup = fakeUiTaskGroup(),
                onEditTaskGroup = { }
            )
        }
    }
}