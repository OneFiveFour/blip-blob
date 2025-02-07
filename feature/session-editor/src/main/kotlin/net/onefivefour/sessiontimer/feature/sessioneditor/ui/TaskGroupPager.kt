package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

@Composable
internal fun TaskGroupPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    uiSession: UiSession,
    onOpenTaskGroupEditor: (Long) -> Unit,
    onAction: (SessionEditorAction) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { pageIndex ->

        val uiTaskGroup = uiSession.taskGroups[pageIndex]

        TaskGroupPage(
            uiTaskGroup = uiTaskGroup,
            onOpenTaskGroupEditor = onOpenTaskGroupEditor,
            onAction = onAction
        )
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
                onOpenTaskGroupEditor = {},
                onAction = {}
            )
        }
    }
}