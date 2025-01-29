package net.onefivefour.sessiontimer.core.ui.label

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R

@Composable
fun LabeledSection(@StringRes labelRes: Int, content: @Composable () -> Unit) {
    Column {
        content()
        LabelLine(
            modifier = Modifier.padding(top = 4.dp),
            labelRes = labelRes
        )
    }
}

@Preview
@Composable
private fun LabeledSectionPreview() {
    SessionTimerTheme {
        Surface {
            LabeledSection(labelRes = R.string.title) {
                Box(Modifier
                    .size(30.dp)
                    .background(Color.Blue))
            }
        }
    }
}