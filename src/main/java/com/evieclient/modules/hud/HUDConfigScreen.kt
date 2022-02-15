package com.evieclient.modules.hud

import com.evieclient.Evie
import com.evieclient.utils.shader.BlurShader
import com.evieclient.utils.ui.EvieLogo
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.resources.I18n

class HUDConfigScreen : GuiScreen() {

    private val evieLogo = EvieLogo()
    private val blurShader = BlurShader()


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

        evieLogo.draw(this.width / 2 - 50, this.height / 2 - 50, 100)

        // drawDefaultBackground()
        blurShader.start()

        for (m in Evie.MODULE_MANAGER.renderModuleList) {
            m.renderDummy(mouseX, mouseY)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}