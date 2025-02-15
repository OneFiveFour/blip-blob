package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import net.onefivefour.sessiontimer.core.ui.utils.toPx
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiSession

private val INDICATOR_SPACING_DP = 24.dp
private val INDICATOR_SIZE_DP = 16.dp

@Composable
internal fun PagerIndicator(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    uiSession: UiSession,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    EnsureIndicatorCentered(
        pagerState = pagerState,
        lazyListState = lazyListState
    )

    LazyRow(
        modifier = modifier,
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(
            INDICATOR_SPACING_DP,
            alignment = Alignment.End
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(uiSession.taskGroups.size) { index ->

            val buttonSize = calculateAnimatedSize(index, pagerState)

            val uiTaskGroup = uiSession.taskGroups[index]

            SquareButton(
                size = buttonSize,
                cornerRadius = 3.dp,
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
private fun EnsureIndicatorCentered(pagerState: PagerState, lazyListState: LazyListState) {
    val itemSize = INDICATOR_SIZE_DP.toPx()
    val itemSpacing = INDICATOR_SPACING_DP.toPx()

    LaunchedEffect(pagerState.currentPage) {
        val targetIndex = pagerState.currentPage

        val viewportWidth =
            lazyListState.layoutInfo.viewportEndOffset -
                lazyListState.layoutInfo.viewportStartOffset

        val currentOffset =
            lazyListState.firstVisibleItemScrollOffset +
                (lazyListState.firstVisibleItemIndex * itemSize) +
                (lazyListState.firstVisibleItemIndex * itemSpacing)

        val itemWidth = targetIndex * itemSize
        val spacingWidth = targetIndex * itemSpacing
        val targetOffset = (itemWidth + spacingWidth + itemSize / 2) - (viewportWidth / 2)
        val scrollBy = targetOffset - currentOffset

        lazyListState.animateScrollBy(
            value = scrollBy,
            animationSpec = tween(durationMillis = 300)
        )
    }
}

@Composable
private fun calculateAnimatedSize(index: Int, pagerState: PagerState): Dp {
    val maxSize = 32.dp
    val halfSize = (maxSize + INDICATOR_SIZE_DP) / 2

    val progress = pagerState.currentPageOffsetFraction

    val isCurrentPage = index == pagerState.currentPage
    val isNextPage = index == pagerState.currentPage + 1
    val isPreviousPage = index == pagerState.currentPage - 1

    return when (progress) {
        in 0f..0.5f -> {
            when {
                isCurrentPage -> lerp(maxSize, halfSize, progress * 2)
                isNextPage -> lerp(INDICATOR_SIZE_DP, halfSize, progress * 2)
                else -> INDICATOR_SIZE_DP
            }
        }

        in -0.5f..0f -> {
            when {
                isCurrentPage -> lerp(maxSize, halfSize, progress * -2)
                isPreviousPage -> lerp(INDICATOR_SIZE_DP, halfSize, progress * -2)
                else -> INDICATOR_SIZE_DP
            }
        }

        else -> INDICATOR_SIZE_DP
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
                pagerState = rememberPagerState { 3 }
            )
        }
    }
}
