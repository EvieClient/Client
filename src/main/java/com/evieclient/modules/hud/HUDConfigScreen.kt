package com.evieclient.modules.hud

import com.evieclient.Evie
import com.evieclient.utils.shader.BlurShader
import com.evieclient.utils.shader.GLRenderer
import com.evieclient.utils.ui.EvieLogo
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import java.awt.Color

class HUDConfigScreen : GuiScreen() {

    private val evieLogo = EvieLogo()

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {

       // make a button that will open the config screen
        this.buttonList.add(
            GuiButton(
                0,
                this.width / 2 - 50,
                this.height / 2 + 50,
                100,
                20,
                "Evie Config"
            )
        )
        // This renders a line at the top of the screen
        GLRenderer.drawLine(
            0,
            0,
            this.width ,
            0,
            6,
        Color(0xFF00BFF)
        )

        // This renders a line at the bottom of the screen
        GLRenderer.drawLine(
            0,
            this.height,
            this.width  ,
            this.height,
            6,
            Color(0xFF00BFF)
        )

        // This renders a line at the left of the screen
        GLRenderer.drawLine(
            0,
            0,
            0,
            this.height,
            6,
            Color(0xFF00BFF)
        )

        // This renders a line at the right of the screen
        GLRenderer.drawLine(
            this.width,
            0,
            this.width,
            this.height,
            6,
            Color(0xFF00BFF)
        )


        evieLogo.draw(this.width / 2 - 50, this.height / 2 - 50, 100)

        // drawDefaultBackground()

        for (m in Evie.MODULE_MANAGER.renderModuleList) {
            m.renderDummy(mouseX, mouseY)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}