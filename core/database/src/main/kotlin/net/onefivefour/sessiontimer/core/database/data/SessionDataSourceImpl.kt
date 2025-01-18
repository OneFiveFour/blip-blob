package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import net.onefivefour.sessiontimer.core.database.SessionQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class SessionDataSourceImpl @Inject constructor(
    private val queries: SessionQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionDataSource {

    override suspend fun insert(title: String) {
        withContext(dispatcher) {
            queries.transaction {
                val maxSortOrder = queries.findMaxSortOrder().executeAsOne().MAX ?: 0L
                val createdAt = Clock.System.now().toEpochMilliseconds()
                queries.new(
                    id = null,
                    title = title,
                    sortOrder = maxSortOrder + 1,
                    createdAt = createdAt
                )
            }
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
