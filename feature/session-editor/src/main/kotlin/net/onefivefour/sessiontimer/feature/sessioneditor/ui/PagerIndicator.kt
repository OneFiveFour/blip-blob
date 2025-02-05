package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession

@Composable
internal fun PagerIndicator(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    uiSession: UiSession,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    LazyRow(
        modifier = modifier,
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(uiSession.taskGroups.size) { index ->

            val animatedSize = calculateAnimatedSize(index, pagerState)

            val uiTaskGroup = uiSession.taskGroups[index]

            SquareButton(
                size = animatedSize,
                backgroundColor = uiTaskGroup.color,
                contentDescription = uiTaskGroup.title
            ) {
                coroutineScope.launch {
                    pagerState.scrollToPage(index)
                }
            }
        }
    }
}

@Composable
private fun calculateAnimatedSize(
    index: Int,
    pagerState: PagerState,
): Dp {

    val minSize = 18.dp
    val maxSize = 32.dp
    val halfSize = (maxSize + minSize) / 2

    val progress = pagerState.currentPageOffsetFraction
    val isCurrentPage = index == pagerState.currentPage

    return when (progress) {
        in 0f..0.5f -> {
            val isTargetPage = index == pagerState.currentPage + 1
            when {
                isCurrentPage -> lerp(maxSize, halfSize, progress * 2)
                isTargetPage -> lerp(minSize, halfSize, progress * 2)
                else -> minSize
            }
        }

        in -0.5f..0f -> {
            val isTargetPage = index == pagerState.currentPage - 1
            when {
                isCurrentPage -> lerp(maxSize, halfSize, progress * -2)
                isTargetPage -> lerp(minSize, halfSize, progress * -2)
                else -> minSize
            }
        }

        else -> minSize
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PagerIndicatorPreview() {
    SessionTimerTheme {
        Surface {
            PagerIndicator(
                lazyListState = LazyListState(),
                uiSession = UiSession(
                    title = "Session Title",
                    taskGroups = listOf(
                        fakeUiTaskGroup(),
                        fakeUiTaskGroup(),
                        fakeUiTaskGroup()
                    )
                ),
                pagerState = rememberPagerState { 3 },
                coroutineScope = rememberCoroutineScope()
            )
        }
    }
}