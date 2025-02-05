package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession

@Composable
internal fun TaskGroupPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    uiSession: UiSession,
    openTaskGroupEditor: (Long) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->

        val taskGroup = uiSession.taskGroups[page]

        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .background(taskGroup.color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.clickable { openTaskGroupEditor(taskGroup.id) },
                text = taskGroup.title, fontSize = 32.sp
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionTitlePreview() {
    SessionTimerTheme {
        Surface {
            TaskGroupPager(
                pagerState = rememberPagerState { 3 },
                uiSession = UiSession(
                    title = "Session Title",
                    taskGroups = listOf(
                        fakeUiTaskGroup(),
                        fakeUiTaskGroup(),
                        fakeUiTaskGroup()
                    )
                ),
                openTaskGroupEditor = {}
            )
        }
    }
}