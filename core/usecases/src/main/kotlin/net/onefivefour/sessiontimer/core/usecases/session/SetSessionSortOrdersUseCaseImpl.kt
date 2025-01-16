package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionSortOrdersUseCase

@ViewModelScoped
class SetSessionSortOrdersUseCaseImpl @Inject constructor(
    private val sessionRepository: SessionRepository
) : SetSessionSortOrdersUseCase {

    override suspend fun execute(sessionIds: List<Long>) {
        sessionRepository.setSessionSortOrders(sessionIds)
    }
}
