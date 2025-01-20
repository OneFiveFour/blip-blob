package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SessionOverviewScreen(openSessionEditor: (Long) -> Unit, openSessionPlayer: (Long) -> Unit) {

    val viewModel: SessionOverviewViewModel = hiltViewModel()
    val sessionOverviewState by viewModel.uiState.collectAsStateWithLifecycle()

    SessionOverview(
        uiState = sessionOverviewState,
        onEditSession = openSessionEditor,
        onStartSession = openSessionPlayer,
        onAction = viewModel::onAction,
    )
}
