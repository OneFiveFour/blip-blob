package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Test

class SessionItemIndicationNodeFactoryTest {

    private val interactionSource: InteractionSource = mockk()

    @Test
    fun `WHEN create is called THEN SessionItemIndicationNode is returned`() {
        // WHEN
        val result = SessionItemIndicationNodeFactory(
            glowColor = Color(0xFFFFFFFF),
            backgroundColor = Color(0xFFFFFFFF)
        ).create(interactionSource)

        // THEN
        assertThat(result).isInstanceOf(SessionItemIndicationNode::class.java)
    }
}
