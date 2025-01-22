package net.onefivefour.sessiontimer.core.ui.buttons

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp


internal fun ContentDrawScope.squareButtonGlow(
    glowColor: Color,
    backgroundColor: Color,
    animatedPercent: Animatable<Float, AnimationVector1D>
) {
    val cornerRadiusPx = 8.dp.toPx()

    val blurRadius = lerp(12.dp, 6.dp, animatedPercent.value).toPx()

    val rectPadding = 12.dp.toPx()

    val paint = Paint().also {
        with(it.asFrameworkPaint()) {
            maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
            color = glowColor.toArgb()
        }
    }

    drawIntoCanvas { canvas ->

        canvas.drawRoundRect(
            left = rectPadding,
            top = rectPadding,
            right = size.width - rectPadding,
            bottom = size.height - rectPadding,
            radiusX = cornerRadiusPx,
            radiusY = cornerRadiusPx,
            paint = paint
        )
    }

    val rectOffset = Offset(
        x = rectPadding,
        y = rectPadding
    )
    val rectHeight = this.size.height - (2 * rectPadding)
    val rectWidth = this.size.width - (2 * rectPadding)

    drawRoundRect(
        size = Size(rectWidth, rectHeight),
        topLeft = rectOffset,
        cornerRadius = CornerRadius(cornerRadiusPx),
        color = backgroundColor,
        alpha = 1f
    )

//    translate(
//        top = animatedTranslate
//    ) {
        this@squareButtonGlow.drawContent()
//    }
}