package net.onefivefour.sessiontimer.core.ui.duration

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DurationViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(DurationState())
    val state = _state.asStateFlow()

    fun onNumberEntered(number: Char) {
        val currentState = _state.value

        val isNumber = number.isDigit()
        val isBackspace = number == '\b'

        if (!isNumber && !isBackspace) {
            return
        }

        when {
            isBackspace -> deleteChar(currentState)
            else -> addChar(currentState, number)
        }
    }

    private fun addChar(
        currentState: DurationState,
        number: Char,
    ) {
        val newSeconds = (currentState.seconds + number).takeLast(2)
        val newMinutes = (currentState.minutes + currentState.seconds.take(1)).takeLast(2)
        val newHours = (currentState.hours + currentState.minutes.take(1)).takeLast(3)

        val totalSeconds =
            newHours.toInt() * 3600 + newMinutes.toInt() * 60 + newSeconds.toInt()

        _state.update {
            it.copy(
                hours = newHours.padStart(3, '0'),
                minutes = newMinutes.padStart(2, '0'),
                seconds = newSeconds.padStart(2, '0'),
                totalSeconds = totalSeconds
            )
        }
    }

    private fun deleteChar(currentState: DurationState) {
        val totalInput = currentState.hours + currentState.minutes + currentState.seconds
        val newInput = totalInput.dropLast(1)

        _state.update { DurationState() }
        newInput.forEach { char ->
            onNumberEntered(char)
        }
    }

    fun updateFromTotalSeconds(totalSeconds: Int) {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        _state.update {
            it.copy(
                hours = hours.toString().padStart(3, '0'),
                minutes = minutes.toString().padStart(2, '0'),
                seconds = seconds.toString().padStart(2, '0'),
                totalSeconds = totalSeconds
            )
        }
    }
}