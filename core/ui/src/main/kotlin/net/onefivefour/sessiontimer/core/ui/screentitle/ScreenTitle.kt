package net.onefivefour.sessiontimer.core.ui.screentitle

import android.content.res.Configuration.*
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R

@Composable
fun ScreenTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 42.dp),
        text = stringResource(titleRes),
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ScreenTitlePreview() {
    SessionTimerTheme {
        Surface {
            ScreenTitle(
                titleRes = R.string.new_task
            )
        }
    }
}