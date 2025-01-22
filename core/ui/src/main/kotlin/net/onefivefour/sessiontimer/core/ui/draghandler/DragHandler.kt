package net.onefivefour.sessiontimer.core.ui.draghandler

import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme

@Composable
fun DragHandler() {
    val numberOfRows = 6

    Column(
        modifier = Modifier.alpha(0.5f)
    ) {
        repeat(numberOfRows) {
            if (it != 0) {
                Spacer(modifier = Modifier.size(1.dp))
            }

            Row {
                Box(
                    modifier = Modifier
                        .size(2.dp)
                        .background(MaterialTheme.colorScheme.onSurface)
                )

                Spacer(modifier = Modifier.size(1.dp))

                Box(
                    modifier = Modifier
                        .size(2.dp)
                        .background(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DragHandlerPreview() {
    SessionTimerTheme {
        Surface {
            DragHandler()
        }
    }
}
