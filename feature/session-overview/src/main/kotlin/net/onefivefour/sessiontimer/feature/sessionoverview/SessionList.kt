package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import net.onefivefour.sessiontimer.core.ui.swipedismiss.SwipeToDismissContainer
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
internal fun SessionList(
    modifier: Modifier,
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
        state = lazyListState
    ) {

        items(
            items = sessionList,
            key = { session -> session.createdAt.toEpochMilliseconds() }
        ) { session ->

            ReorderableItem(reorderableLazyColumnState, session.id) {
                val interactionSource = remember { MutableInteractionSource() }

//                SwipeToDismissContainer(
//                    item = session,
//                    onDelete = { onAction(SessionOverviewAction.DeleteSession(session.id)) }
//                ) {

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
//            }
        }
    }
}