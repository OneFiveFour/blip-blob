package net.onefivefour.sessiontimer.core.ui.swipedismiss

//import androidx.compose.ui.graphics.lerp as lerpColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp as lerpColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp as lerpDp
import net.onefivefour.sessiontimer.core.ui.R

@Composable
fun <T> SwipeToDismissContainer(
    modifier: Modifier = Modifier,
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
            totalDistance * 0.5f
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
                DeleteBackground(
                    modifier = modifier,
                    swipeDismissState = state
                )
            },
            enableDismissFromStartToEnd = true,
        ) {
            content()
        }
    }
}

@Composable
private fun DeleteBackground(
    modifier: Modifier,
    swipeDismissState: SwipeToDismissBoxState
) {

    val isStartToEnd = swipeDismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd
    val backgroundColor = when {
        isStartToEnd -> lerpColor(
            start = MaterialTheme.colorScheme.background,
            stop = MaterialTheme.colorScheme.error,
            fraction = swipeDismissState.progress * 3
        )
        else -> Color.Transparent
    }
    val textOffset = when {
        isStartToEnd -> lerpDp(
            start = (-12).dp,
            stop = 0.dp,
            fraction = swipeDismissState.progress * 2
        )
        else -> 0.dp
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(start = 24.dp)
        ,
        contentAlignment = Alignment.CenterStart
    ) {

        Text(
            modifier = Modifier.offset(x = textOffset),
            text = stringResource(R.string.delete),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onError
        )
    }
}