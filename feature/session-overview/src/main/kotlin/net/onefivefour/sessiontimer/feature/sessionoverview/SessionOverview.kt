package net.onefivefour.sessiontimer.feature.sessionoverview

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R as UiR
import net.onefivefour.sessiontimer.core.ui.components.button.PrimaryButton
import net.onefivefour.sessiontimer.core.ui.haptic.ReorderHapticFeedbackType
import net.onefivefour.sessiontimer.core.ui.haptic.rememberReorderHapticFeedback
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
internal fun SessionOverview(
    uiState: UiState,
    onEditSession: (Long) -> Unit,
    onNewSession: () -> Unit,
    onUpdateSessionSortOrders: (List<Long>) -> Unit,
    onStartSession: (Long) -> Unit
) {
    if (uiState == UiState.Initial) {
        SessionOverviewInitial()
        return
    }

    if (uiState !is UiState.Success) {
        return
    }

    Column(
        Modifier.fillMaxSize().padding(
            bottom = 16.dp,
            start = 16.dp,
            end = 16.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.sessions),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.padding(16.dp))

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
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = lazyListState
        ) {
            items(
                items = sessionList,
                key = { session -> session.id }
            ) { session ->

                ReorderableItem(reorderableLazyColumnState, session.id) {
                    val interactionSource = remember { MutableInteractionSource() }

                    SessionItem(
                        modifier = Modifier
                            .longPressDraggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                    onUpdateSessionSortOrders(sessionList.map { it.id })
                                },
                                interactionSource = interactionSource
                            ),
                        session = session,
                        onStartSession = onStartSession,
                        onEditSession = onEditSession
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        PrimaryButton(
            text = stringResource(id = R.string.new_session),
            iconRes = UiR.drawable.ic_add,
            contentDescription = stringResource(id = R.string.new_session),
            onClick = { onNewSession() }
        )

        // avoid unused var error
        println("+++ remove me: $onUpdateSessionSortOrders")
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SessionOverviewPreview() {
    SessionTimerTheme {
        SessionOverview(
            uiState = UiState.Success(
                listOf(
                    UiSession(1, "A session", 1),
                    UiSession(1, "A session", 2),
                    UiSession(1, "A session", 3),
                    UiSession(1, "A session", 4)
                )
            ),
            onEditSession = {},
            onNewSession = {},
            onUpdateSessionSortOrders = { _ -> },
            onStartSession = { _ -> }
        )
    }
}
