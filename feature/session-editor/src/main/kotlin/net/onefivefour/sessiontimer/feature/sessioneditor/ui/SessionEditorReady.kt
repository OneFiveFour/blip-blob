package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import net.onefivefour.sessiontimer.core.ui.label.LabeledSection
import net.onefivefour.sessiontimer.core.ui.modifier.clearFocusOnKeyboardDismiss
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.core.ui.utils.topToAscentDp
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorAction

@Composable
internal fun SessionEditorReady(
    uiSession: UiSession,
    onAction: (SessionEditorAction) -> Unit,
    openTaskGroupEditor: (Long) -> Unit,
) {

    val pagerState = rememberPagerState { uiSession.taskGroups.size }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
    ) {

        ScreenTitle(titleRes = R.string.edit_session)

        LabeledSection(R.string.title) {
            val textStyle = MaterialTheme.typography.titleMedium
            val offset = textStyle.topToAscentDp() - 4.dp

            val textFieldState = rememberTextFieldState()

            LaunchedEffect(uiSession.title) {
                textFieldState.edit {
                    replace(
                        start = 0,
                        end = length,
                        text = uiSession.title
                    )
                }
            }
            BasicTextField(
                modifier = Modifier
                    .zIndex(1f)
                    .offset(y = offset)
                    .fillMaxWidth()
                    .clearFocusOnKeyboardDismiss(),
                inputTransformation = {
                    val newTitle = asCharSequence().toString()
                    onAction(SessionEditorAction.SetSessionTitle(newTitle))
                },
                state = textFieldState,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.titleMedium
                    .copy(color = MaterialTheme.colorScheme.onSurface),
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            Box(
                modifier =
                Modifier
                    .padding(10.dp)
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.clickable { openTaskGroupEditor(uiSession.taskGroups[page].id) },
                    text = uiSession.taskGroups[page].title, fontSize = 32.sp
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                items(uiSession.taskGroups.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    val size = if (isSelected) 12.dp else 8.dp
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(size)
                            .background(
                                color = uiSession.taskGroups[index].color,
                                shape = CircleShape
                            )
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            SquareButton(
                iconRes = R.drawable.ic_add,
                contentDescriptionRes = R.string.new_task_group,
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
