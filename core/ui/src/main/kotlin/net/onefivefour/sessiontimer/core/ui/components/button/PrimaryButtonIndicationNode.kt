package net.onefivefour.sessiontimer.core.ui.components.button

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class PrimaryButtonIndicationNode(
    private val interactionSource: InteractionSource,
    private val backgroundColor: Color,
    private val glowColor: Color
) : Modifier.Node(), DrawModifierNode {

    private val animatedPercent = Animatable(0f)

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> animateToPressed()
                    is PressInteraction.Release -> animateToResting()
                    is PressInteraction.Cancel -> animateToResting()
                }
            }
        }
    }

    private suspend fun animateToPressed() {
        animatedPercent.animateTo(
            targetValue = 1f,
            animationSpec = tween(150)
        )
    }

    private suspend fun animateToResting() {
        animatedPercent.animateTo(
            targetValue = 0f,
            animationSpec = spring()
        )
    }

    override fun ContentDrawScope.draw() {
        val cornerRadiusPx = 8.dp.toPx()
        val blurRadius = 6.dp.toPx()
        val paint = Paint().also {
            with(it.asFrameworkPaint()) {
                maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
                color = glowColor.toArgb()
            }
        }
        val rectPadding = 6.dp.toPx()

        val animatedTranslate = animatedPercent.value * 2.dp.toPx()

        drawIntoCanvas { canvas ->

            canvas.drawRoundRect(
                left = rectPadding - 2.dp.toPx() + animatedTranslate,
                top = rectPadding + 2.dp.toPx(),
                right = size.width * 0.23f + animatedTranslate,
                bottom = size.height - rectPadding - 2.dp.toPx(),
                radiusX = cornerRadiusPx,
                radiusY = cornerRadiusPx,
                paint = paint
            )

            canvas.drawRoundRect(
                left = size.width * 0.77f - animatedTranslate,
                top = rectPadding + 2.dp.toPx(),
                right = size.width - rectPadding + 2.dp.toPx() - animatedTranslate,
                bottom = size.height - rectPadding - 2.dp.toPx(),
                radiusX = cornerRadiusPx,
                radiusY = cornerRadiusPx,
                paint = paint
            )
        }

        val rectOffset = Offset(
            x = rectPadding + animatedTranslate,
            y = rectPadding
        )
        val rectHeight = this.size.height - (2 * rectPadding)
        val rectWidth = this.size.width - (2 * rectPadding)

        drawRoundRect(
            size = Size(rectWidth - (animatedTranslate * 2f), rectHeight),
            topLeft = rectOffset,
            cornerRadius = CornerRadius(cornerRadiusPx),
            color = backgroundColor,
            alpha = 1f
        )

        translate(
            top = animatedTranslate
        ) {
            this@draw.drawContent()
        }
    }
}
