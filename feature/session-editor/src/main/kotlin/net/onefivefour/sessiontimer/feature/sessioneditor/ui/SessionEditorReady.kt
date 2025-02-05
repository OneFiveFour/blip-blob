package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction


@Composable
private fun EnsurePageIndicatorVisible(
    pagerState: PagerState,
    lazyListState: LazyListState,
    coroutineScope: CoroutineScope
) {
    LaunchedEffect(pagerState.currentPage) {
        val visibleItems = lazyListState.layoutInfo.visibleItemsInfo.map { it.index }
        if (pagerState.currentPage !in visibleItems) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(
                    pagerState.currentPage,
                    scrollOffset = 0
                )
            }
        }
    }
}


@Composable
internal fun SessionEditorReady(
    uiSession: UiSession,
    onAction: (SessionEditorAction) -> Unit,
    openTaskGroupEditor: (Long) -> Unit
) {

    val pagerState = rememberPagerState { uiSession.taskGroups.size }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    EnsurePageIndicatorVisible(
        pagerState = pagerState,
        lazyListState = lazyListState,
        coroutineScope = coroutineScope
    )

    Column(modifier = Modifier.fillMaxSize()) {

        ScreenTitle(titleRes = R.string.edit_session)

        SessionTitle(
            uiSession = uiSession,
            onAction = onAction
        )

        TaskGroupPager(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            pagerState = pagerState,
            uiSession = uiSession,
            openTaskGroupEditor = openTaskGroupEditor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp)
                .height(64.dp),
            horizontalArrangement = Arrangement.End
        ) {

            PagerIndicator(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                lazyListState = lazyListState,
                uiSession = uiSession,
                pagerState = pagerState,
                coroutineScope = coroutineScope
            )

            Spacer(modifier = Modifier.size(16.dp))

            SquareButton(
                iconRes = R.drawable.ic_add,
                contentDescription = stringResource(R.string.new_task_group),
                onClick = { onAction(SessionEditorAction.CreateTaskGroup) }
            )
        }
    }
}

@Preview
@Composable
private fun SessionEditorReadyPreview() {
    SessionTimerTheme {
        Surface {
            SessionEditorReady(
                uiSession = UiSession(
                    title = "Session Title",
                    taskGroups = listOf(
                        fakeUiTaskGroup(),
                        fakeUiTaskGroup(),
                        fakeUiTaskGroup()
                    )
                ),
                onAction = { },
                openTaskGroupEditor = { }
            )
        }
    }
}
