package net.onefivefour.sessiontimer.core.ui.sqarebutton

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class SquareButtonIndicationNode(
    private val interactionSource: InteractionSource,
    private val backgroundColor: Color,
    private val glowColor: Color
) : Modifier.Node(), DrawModifierNode {

    private val animatedPercent: Animatable<Float, AnimationVector1D> = Animatable(0f)

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
        squareButtonGlow(
            glowColor = glowColor,
            backgroundColor = backgroundColor,
            animatedPercent = animatedPercent
        )
    }
}
