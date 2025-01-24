package net.onefivefour.sessiontimer.core.ui.swipedismiss

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.ui.sqarebutton.SquareButton

@Composable
fun <T> SwipeToDismissContainer(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable () -> Unit
) {

    var isRemoved by remember {
        mutableStateOf(false)
    }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    isRemoved = true
                    true
                }
                else -> false
            }
        },
        positionalThreshold = { totalDistance ->
            totalDistance * 0.6f
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeDismissState = state)
            },
            enableDismissFromStartToEnd = true,
        ) {
            content()
        }
    }
}

@Composable
private fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {

    val background = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
        MaterialTheme.colorScheme.error
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onError
        )
    }
}