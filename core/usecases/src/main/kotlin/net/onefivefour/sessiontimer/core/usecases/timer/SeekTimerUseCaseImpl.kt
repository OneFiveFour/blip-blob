package net.onefivefour.sessiontimer.core.usecases.timer

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.usecases.api.timer.SeekTimerUseCase

@ViewModelScoped
class SeekTimerUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
) : SeekTimerUseCase {

    override fun execute(totalDuration: Duration) {
        println("+++ seek to ${totalDuration.inWholeSeconds}s")
        sessionTimer.seekTo(totalDuration)
    }
}
