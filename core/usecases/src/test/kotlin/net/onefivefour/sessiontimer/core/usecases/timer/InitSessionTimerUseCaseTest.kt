package net.onefivefour.sessiontimer.core.usecases.timer

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerState
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class InitSessionTimerUseCaseTest {

    private val sessionTimer: SessionTimer = mockk(relaxed = true)

    private fun sut() = InitSessionTimerUseCaseImpl(
        sessionTimer
    )

    @Test
    fun `WHEN executing the UseCase THEN it is calling init on the sessionTimer`() {
        // WHEN
        val totalDuration = 3.seconds
        sut().execute(totalDuration)

        // THEN
        verify(exactly = 1) { sessionTimer.init(totalDuration) }
    }
}
