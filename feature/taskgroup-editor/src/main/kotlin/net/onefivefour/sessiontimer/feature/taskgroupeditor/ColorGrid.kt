package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.taskGroupColors

@Composable
internal fun ColorGrid(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    selectedColor: Color,
    columnsCount: Int,
    gapSize: Dp,
    onColorClick: (Color) -> Unit,
) {

    fun darkenColor(color: Color): Color {
        return color.copy(
            alpha = 1f,
            red = color.red / 2,
            green = color.green / 2,
            blue = color.blue / 2
        )
    }

    val rowsCount = (colors.size + columnsCount - 1) / columnsCount

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(gapSize)
    ) {
        repeat(rowsCount) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gapSize)
            ) {
                val startIndex = rowIndex * columnsCount
                val endIndex = minOf(startIndex + columnsCount, colors.size)
                val rowColors = colors.subList(startIndex, endIndex)

                rowColors.forEach { color ->

                    val isSelectedColor = color == selectedColor

                    val borderShape = when {
                        isSelectedColor -> RoundedCornerShape(14.dp)
                        else -> RoundedCornerShape(4.dp)
                    }

                    val borderColor = when {
                        isSelectedColor -> darkenColor(color)
                        else -> color
                    }

                    Box(
                        modifier = Modifier
                            .size(TILE_SIZE)
                            .background(
                                color = color,
                                shape = borderShape
                            )
                            .border(
                                shape = borderShape,
                                width = if (isSelectedColor) 2.dp else 0.dp,
                                color = borderColor
                            )
                            .clickable { onColorClick(color) }
                    )
                }
            }
        }
    }


}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ColorGridPreview() {
    SessionTimerTheme {
        Surface {
            val colors = MaterialTheme.taskGroupColors.getAll()

            ColorGrid(
                colors = colors,
                selectedColor = MaterialTheme.taskGroupColors.color12,
                columnsCount = 5,
                gapSize = 8.dp,
                onColorClick = { }
            )
        }
    }
}