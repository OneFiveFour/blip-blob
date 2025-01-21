package net.onefivefour.sessiontimer.feature.sessionplayer.ui

internal sealed class SessionPlayerAction {
    data object StartSession : SessionPlayerAction()
    data object PauseSession : SessionPlayerAction()
    data object ResetSession : SessionPlayerAction()
    data object NextTask : SessionPlayerAction()
    data object PreviousTask : SessionPlayerAction()
}