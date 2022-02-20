package com.evieclient.modules.hud

import com.evieclient.Evie
import com.evieclient.utils.saving.Save
import com.evieclient.utils.ui.UIUtils
import net.minecraft.client.gui.GuiScreen

class EvieConfigScreen : GuiScreen() {

    override fun onGuiClosed() {
        Save.saveConfig()
        super.onGuiClosed()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Evie.evieLogo.drawPinkBars(this.width, this.height)
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}