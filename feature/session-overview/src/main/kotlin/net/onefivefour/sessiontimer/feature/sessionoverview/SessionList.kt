package net.onefivefour.sessiontimer.feature.sessionoverview

import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.swipedismiss.SwipeToDismissContainer
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
internal fun SessionList(
    modifier: Modifier = Modifier,
    uiState: UiState.Ready,
    onAction: (SessionOverviewAction) -> Unit,
    onStartSession: (Long) -> Unit,
    onEditSession: (Long) -> Unit
) {
    val haptic = rememberReorderHapticFeedback()

    var sessionList by remember(uiState.sessions) { mutableStateOf(uiState.sessions) }

    val lazyListState = rememberLazyListState()

    val reorderableLazyColumnState =
        rememberReorderableLazyListState(lazyListState) { from, to ->
            sessionList = sessionList.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
        }

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(38.dp)
    ) {

        items(
            items = sessionList,
            key = { session -> session.createdAt.toEpochMilliseconds() }
        ) { session ->

            ReorderableItem(reorderableLazyColumnState, session.createdAt.toEpochMilliseconds()) {

                val interactionSource = remember { MutableInteractionSource() }

                SwipeToDismissContainer(
                    item = session,
                    onDelete = { onAction(SessionOverviewAction.DeleteSession(session.id)) }
                ) {

                    SessionItem(
                        modifier = Modifier
                            .longPressDraggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                    val sessionIds = sessionList.map { it.id }
                                    onAction(
                                        SessionOverviewAction.UpdateSessionSortOrders(sessionIds)
                                    )
                                },
                                interactionSource = interactionSource
                            ),
                        uiSession = session,
                        onStartSession = onStartSession,
                        onEditSession = onEditSession
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SessionListPreview() {
    SessionTimerTheme {
        Surface {
            SessionList(
                uiState = UiState.Ready(uiSessionList),
                onAction = {  },
                onStartSession = {  },
                onEditSession = { _ -> }
            )
        }
    }
}