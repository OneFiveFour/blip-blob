package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun TaskItem(
    modifier: Modifier = Modifier,
    task: UiTask
) {

    Row(modifier = modifier.height(TASK_ITEM_HEIGHT)) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = task.title
        )
    }
}
