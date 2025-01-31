package net.onefivefour.sessiontimer.core.ui.duration

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.R

internal val TILE_SIZE = 60.dp

@Composable
fun DurationInput(
    modifier: Modifier = Modifier,
    defaultTaskDuration: String,
    onDurationEntered: (String) -> Unit,
) {

    var isFocused by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    val textFieldState = rememberTextFieldState()

    LaunchedEffect(defaultTaskDuration) {
        textFieldState.edit {
            replace(
                start = 0,
                end = length,
                text = defaultTaskDuration
            )
        }
    }

    Box {

        // Hidden text field to capture keyboard input
        CompositionLocalProvider(
            LocalTextToolbar provides EmptyTextToolbar,
            LocalTextSelectionColors provides TextSelectionColors(
                handleColor = Color.Transparent,
                backgroundColor = Color.Transparent,
            )
        ) {
            BasicTextField(
                state = textFieldState,
                inputTransformation = {
                    val digits = asCharSequence()
                        .filter { it.isDigit() }
                        .takeLast(6)
                        .padStart(6, '0')
                        .toString()

                    replace(0, length, digits)

                    onDurationEntered(digits)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                cursorBrush = SolidColor(Color.Transparent),
                modifier = Modifier
                    .alpha(0f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                textStyle = MaterialTheme.typography.labelSmall
                    .copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    ),
                decorator = { innerTextField ->
                    CenteredTextBox(isFocused) {
                        innerTextField()
                    }
                }
            )
        }

        Row(
            modifier = modifier
                .clickable {
                    isFocused = true
                    focusRequester.requestFocus()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Hours
            CenteredTextBox(isFocused) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = textFieldState.text.take(2).toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }


            Text(
                text = stringResource(R.string.colon),
                style = MaterialTheme.typography.labelSmall
            )

            // Minutes
            CenteredTextBox(isFocused) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = textFieldState.text.dropLast(2).takeLast(2).toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = stringResource(R.string.colon),
                style = MaterialTheme.typography.labelSmall
            )

            // Seconds
            CenteredTextBox(isFocused) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = textFieldState.text.takeLast(2).toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
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
                defaultTaskDuration = "010203",
                onDurationEntered = { }
            )
        }
    }
}