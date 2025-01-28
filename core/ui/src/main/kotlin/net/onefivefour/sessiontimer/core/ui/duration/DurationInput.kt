package net.onefivefour.sessiontimer.core.ui.duration

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.KeyEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal val TILE_SIZE = 60.dp

@Composable
fun DurationInput(
    modifier: Modifier = Modifier,
    hours: String,
    minutes: String,
    seconds: String,
    onNumberEntered: (String, Char) -> Unit,
) {

    val hiddenTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent,
    )

    var isFocused = remember { false }

    val focusRequester = remember { FocusRequester() }

    val currentString = hours + minutes + seconds

    CompositionLocalProvider(
        LocalTextToolbar provides EmptyTextToolbar,
        LocalTextSelectionColors provides hiddenTextSelectionColors
    ) {

        Row(
            modifier = modifier
                .clickable {
                    isFocused = true
                    focusRequester.requestFocus()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CenteredTextBox(isFocused) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = hours,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }


            Text(
                text = stringResource(R.string.colon),
                style = MaterialTheme.typography.labelSmall
            )

            CenteredTextBox(isFocused) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = minutes,
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
                value = TextFieldValue(seconds),
                onValueChange = { newNumber ->
                    if (newNumber.text.isNotEmpty()) {
                        val numberEntered = newNumber.text.first()
                        onNumberEntered(currentString, numberEntered)
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
                            onNumberEntered(currentString, '\b')
                        }
                        false
                    }
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                textStyle = MaterialTheme.typography.labelSmall
                    .copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    ),

                ) { innerTextField ->
                CenteredTextBox(isFocused) {
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
            DurationInput(
                hours = "001",
                minutes = "02",
                seconds = "03",
                onNumberEntered = { _, _ -> }
            )
        }
    }
}