package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.keyboard.keyboardAsState
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

internal val LocalTaskEditMode =
    compositionLocalOf { mutableStateOf<TaskEditMode>(TaskEditMode.None) }

@Composable
internal fun SessionEditorReady(
    uiSession: UiSession,
    onAction: (SessionEditorAction) -> Unit,
    openTaskGroupEditor: (Long) -> Unit,
) {

    val pagerState = rememberPagerState { uiSession.taskGroups.size }

    val lazyListState = rememberLazyListState()

    val taskEditMode = remember { mutableStateOf<TaskEditMode>(TaskEditMode.None) }

    CompositionLocalProvider(LocalTaskEditMode provides taskEditMode) {

        Column(modifier = Modifier.fillMaxSize()) {

            ScreenTitle(titleRes = R.string.edit_session)

            SessionTitle(
                uiSession = uiSession,
                onAction = onAction
            )
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->

                val uiTaskGroup = uiSession.taskGroups[pageIndex]

                TaskGroupPage(
                    uiTaskGroup = uiTaskGroup,
                    openTaskGroupEditor = openTaskGroupEditor,
                    onAction = onAction
                )
            }

            AnimatedVisibility(!taskEditMode.value.isEditing) {
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
                        pagerState = pagerState
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
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
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
