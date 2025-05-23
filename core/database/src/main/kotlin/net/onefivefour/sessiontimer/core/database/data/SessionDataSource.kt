package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.DenormalizedSessionView
import net.onefivefour.sessiontimer.core.database.Session

internal interface SessionDataSource {

    fun insert(title: String)

    fun getAll(): Flow<List<Session>>

    suspend fun getDenormalizedSession(sessionId: Long): Flow<List<DenormalizedSessionView>>

    suspend fun deleteById(sessionId: Long)

    suspend fun setTitle(sessionId: Long, title: String)

    suspend fun setSortOrders(sessionIds: List<Long>)

    fun getLastInsertId(): Long
}
