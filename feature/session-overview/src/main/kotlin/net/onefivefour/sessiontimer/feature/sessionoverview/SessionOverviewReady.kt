package net.onefivefour.sessiontimer.feature.sessionoverview

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.screentitle.ScreenTitle
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.ui.R as UiR

@Composable
internal fun SessionOverviewReady(
    uiState: UiState.Ready,
    onEditSession: (Long) -> Unit,
    onStartSession: (Long) -> Unit,
    onAction: (SessionOverviewAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
    ) {
        ScreenTitle(titleRes = R.string.sessions)

        SessionList(
            modifier = Modifier.weight(1f),
            uiState = uiState,
            onAction = onAction,
            onStartSession = onStartSession,
            onEditSession = onEditSession
        )

        SquareButton(
            modifier = Modifier.align(Alignment.End),
            iconRes = UiR.drawable.ic_add,
            contentDescriptionRes = R.string.new_session,
            onClick = { onAction(SessionOverviewAction.CreateSession) }
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SessionOverviewReadyPreview() {
    SessionTimerTheme {
        Surface {
            val now = Clock.System.now()
            SessionOverviewReady(
                uiState = UiState.Ready(
                    listOf(
                        UiSession(
                            id = 1,
                            title = "A session",
                            sortOrder = 1,
                            createdAt = now.plus(1.seconds)
                        ),
                        UiSession(
                            id = 1,
                            title = "A session",
                            sortOrder = 2,
                            createdAt = now.plus(2.seconds)
                        ),
                        UiSession(
                            id = 1,
                            title = "A session",
                            sortOrder = 3,
                            createdAt = now.plus(3.seconds)
                        ),
                        UiSession(
                            id = 1,
                            title = "A session",
                            sortOrder = 4,
                            createdAt = now.plus(4.seconds)
                        )
                    )
                ),
                onEditSession = {},
                onStartSession = {},
                onAction = {}
            )
        }
    }
}