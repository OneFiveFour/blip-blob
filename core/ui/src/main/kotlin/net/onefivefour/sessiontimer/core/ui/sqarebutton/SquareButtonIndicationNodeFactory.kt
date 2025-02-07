package net.onefivefour.sessiontimer.core.ui.sqarebutton

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.unit.Dp

class SquareButtonIndicationNodeFactory(
    private val glowColor: Color,
    private val backgroundColor: Color,
    private val cornerRadius: Dp
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return SquareButtonIndicationNode(
            interactionSource = interactionSource,
            backgroundColor = backgroundColor,
            glowColor = glowColor,
            cornerRadius = cornerRadius
        )
    }

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this
}
