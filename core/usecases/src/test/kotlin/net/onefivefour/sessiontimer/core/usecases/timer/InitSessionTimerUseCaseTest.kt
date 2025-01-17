package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.Test

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
