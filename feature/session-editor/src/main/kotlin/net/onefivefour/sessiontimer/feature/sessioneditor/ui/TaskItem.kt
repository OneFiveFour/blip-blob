package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask

@Composable
internal fun TaskItem(modifier: Modifier = Modifier, task: UiTask) {
    Row(modifier = modifier.height(TASK_ITEM_HEIGHT)) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = task.title
        )
    }
}
