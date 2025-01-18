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
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionTitleUseCase

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
                    UiState.Success(uiSessions)
                }
            }
        }
    }

    fun newSession() {
        viewModelScope.launch {
            newSessionUseCase.execute()
        }
    }

    fun updateSessionSortOrders(sessionIds: List<Long>) {
        viewModelScope.launch {
            setSessionSortOrdersUseCase.execute(sessionIds)
        }
    }

    fun onDeleteSession(sessionId: Long) {
        viewModelScope.launch {
            deleteSessionUseCase.execute(sessionId)
        }
    }
}
