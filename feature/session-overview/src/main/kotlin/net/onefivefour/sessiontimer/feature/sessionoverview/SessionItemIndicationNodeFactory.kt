package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.DelegatableNode

internal class SessionItemIndicationNodeFactory(
    private val glowColor: Color,
    private val backgroundColor: Color
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return SessionItemIndicationNode(
            interactionSource = interactionSource,
            backgroundColor = backgroundColor,
            glowColor = glowColor
        )
    }

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this
}
