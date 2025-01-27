package net.onefivefour.sessiontimer.core.ui.duration

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R

@Composable
fun DurationInputContainer(modifier: Modifier = Modifier) {
    val viewModel: DurationViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    DurationInput(
        modifier = modifier,
        state = state.value,
        onNumberEntered = viewModel::onNumberEntered
    )
}

internal val TILE_SIZE = 60.dp

@Composable
fun CenteredTextBox(content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(TILE_SIZE)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.onSurfaceVariant)
    ) {
        content()
    }
}

@Composable
fun DurationInput(
    modifier: Modifier = Modifier,
    state: DurationState,
    onNumberEntered: (Char) -> Unit,

    ) {

    val hiddenTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent,
    )

    CompositionLocalProvider(
        LocalTextToolbar provides EmptyTextToolbar,
        LocalTextSelectionColors provides hiddenTextSelectionColors
    ) {

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CenteredTextBox {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.hours,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }


            Text(
                text = stringResource(R.string.colon),
                style = MaterialTheme.typography.labelSmall
            )

            CenteredTextBox {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.hours,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = stringResource(R.string.colon),
                style = MaterialTheme.typography.labelSmall
            )

            BasicTextField(
                value = TextFieldValue(state.seconds),
                onValueChange = { newNumber ->
                    if (newNumber.text.isNotEmpty()) {
                        onNumberEntered(newNumber.text.first())
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                cursorBrush = SolidColor(Color.Transparent),
                modifier = Modifier
                    .onKeyEvent { event ->
                        val didPressDelete =
                            event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                        if (didPressDelete) {
                            onNumberEntered('\b')
                        }
                        false
                    },
                textStyle = MaterialTheme.typography.labelSmall
                    .copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    ),

                ) { innerTextField ->
                CenteredTextBox {
                    innerTextField()
                }
            }

        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DurationInputPreview() {
    SessionTimerTheme {
        Surface {
            val state = DurationState(
                hours = "01",
                minutes = "02",
                seconds = "03"
            )
            DurationInput(
                state = state,
                onNumberEntered = { }
            )
        }
    }
}