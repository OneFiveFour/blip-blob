package net.onefivefour.sessiontimer.core.ui.glow

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

fun ContentDrawScope.drawGlowingSides(
    glowColor: Color,
    backgroundColor: Color,
    blurRadius: Float = 12.dp.toPx(),
    animatedPercent: Animatable<Float, AnimationVector1D>? = null
) {
    val cornerRadiusPx = 8.dp.toPx()
    val paint = Paint().also {
        with(it.asFrameworkPaint()) {
            maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
            color = glowColor.toArgb()
        }
    }
    val rectPadding = 10.dp.toPx()

    val animatedTranslate = when (animatedPercent) {
        null -> 0f
        else -> animatedPercent.value * 2.dp.toPx()
    }

    drawIntoCanvas { canvas ->

        canvas.drawRoundRect(
            left = rectPadding - 2.dp.toPx() + animatedTranslate,
            top = rectPadding + 2.dp.toPx(),
            right = size.width * 0.25f + animatedTranslate,
            bottom = size.height - rectPadding - 2.dp.toPx(),
            radiusX = cornerRadiusPx,
            radiusY = cornerRadiusPx,
            paint = paint
        )

        canvas.drawRoundRect(
            left = size.width * 0.75f - animatedTranslate,
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
        this@drawGlowingSides.drawContent()
    }
}