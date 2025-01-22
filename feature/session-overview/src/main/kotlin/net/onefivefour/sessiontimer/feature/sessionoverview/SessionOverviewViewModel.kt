package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.usecases.api.session.DeleteSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.GetAllSessionsUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.NewSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionSortOrdersUseCase

@HiltViewModel
internal class SessionOverviewViewModel @Inject constructor(
    private val getAllSessionsUseCase: GetAllSessionsUseCase,
    private val newSessionUseCase: NewSessionUseCase,
    private val setSessionSortOrdersUseCase: SetSessionSortOrdersUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllSessionsUseCase.execute().collectLatest { sessions ->
                _uiState.update {
                    val uiSessions = sessions.toUiSessions()
                    UiState.Ready(uiSessions)
                }
            }
        }
    }

    fun onAction(action: SessionOverviewAction) {
        when (action) {
            is SessionOverviewAction.CreateSession -> createNewSession()
            is SessionOverviewAction.UpdateSessionSortOrders -> updateSessionSortOrders(action.sessionIds)
            is SessionOverviewAction.DeleteSession -> onDeleteSession(action.sessionId)
        }
    }

    private fun createNewSession() {
        viewModelScope.launch {
            newSessionUseCase.execute()
        }
    }

    private fun updateSessionSortOrders(sessionIds: List<Long>) {
        viewModelScope.launch {
            setSessionSortOrdersUseCase.execute(sessionIds)
        }
    }

    private fun onDeleteSession(sessionId: Long) {
        viewModelScope.launch {
            deleteSessionUseCase.execute(sessionId)
        }
    }
}
