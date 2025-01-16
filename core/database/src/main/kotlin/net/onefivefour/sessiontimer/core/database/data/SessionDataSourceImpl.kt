package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.database.SessionQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class SessionDataSourceImpl @Inject constructor(
    private val queries: SessionQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : SessionDataSource {

    override suspend fun insert(title: String, sortOrder: Long) {
        withContext(dispatcher) {
            queries.new(
                id = null,
                title = title,
                sortOrder = sortOrder
            )
        }
    }

    override fun getAll() = queries
        .getAll()
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun getDenormalizedSession(sessionId: Long) = queries
        .denormalizedSessionView(sessionId)
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun deleteById(sessionId: Long) {
        withContext(dispatcher) {
            queries.deleteById(sessionId)
        }
    }

    override suspend fun setTitle(sessionId: Long, title: String) {
        withContext(dispatcher) {
            queries.setTitle(
                sessionId = sessionId,
                title = title
            )
        }
    }

    override suspend fun setSortOrders(sessionIds: List<Long>) {
        withContext(dispatcher) {
            queries.transaction {
                sessionIds.forEachIndexed { index, sessionId ->
                    queries.setSortOrder(
                        sessionId = sessionId,
                        sortOrder = index.toLong()
                    )
                }
            }
        }
    }

    override fun getLastInsertId() = queries
        .getLastInsertRowId()
        .executeAsOne()
}
