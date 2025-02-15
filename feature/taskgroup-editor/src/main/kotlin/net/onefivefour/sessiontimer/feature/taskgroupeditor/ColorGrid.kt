package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
    colors: List<Pair<Color, Color>>,
    selectedColor: Color,
    columnsCount: Int,
    gapSize: Dp,
    onColorClick: (Color, Color) -> Unit
) {
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

                for (index in startIndex until endIndex) {
                    val color = colors[index].first
                    val onColor = colors[index].second
                    ColorTile(
                        color = color,
                        isSelected = color == selectedColor,
                        onClick = { onColorClick(color, onColor) }
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
                selectedColor = MaterialTheme.taskGroupColors.color12.first,
                columnsCount = 5,
                gapSize = 8.dp,
                onColorClick = { _, _ -> }
            )
        }
    }
}
