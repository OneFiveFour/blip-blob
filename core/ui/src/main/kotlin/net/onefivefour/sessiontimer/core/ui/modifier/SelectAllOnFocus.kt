package net.onefivefour.sessiontimer.core.ui.modifier

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.selectAll
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import kotlinx.coroutines.launch

fun Modifier.selectAllOnFocus(textFieldState: TextFieldState): Modifier = composed {

    val coroutineScope = rememberCoroutineScope()

    this.onFocusChanged { focusState ->
        if (focusState.isFocused) {
            coroutineScope.launch {
                textFieldState.edit {
                    selectAll()
                }
            }
        }
    }
}
