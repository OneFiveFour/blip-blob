package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.taskgroupeditor.R
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme

@Composable
internal fun LabelLine(
    modifier: Modifier = Modifier,
    @StringRes labelRes: Int
) {
    Column(modifier = modifier.padding(bottom = 24.dp)) {
        Spacer(
            modifier = modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Text(
            modifier = Modifier.offset(y = (-4).dp),
            text = stringResource(labelRes).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LabelLinePreview() {
    SessionTimerTheme {
        Surface {
            LabelLine(
                labelRes = R.string.title
            )
        }
    }
}