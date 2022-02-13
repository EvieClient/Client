package com.evieclient.modules.hud

import net.minecraft.client.gui.GuiScreen
import com.evieclient.Evie
import java.awt.Color

class HUDConfigScreen : GuiScreen() {
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        for (m in Evie.MODULE_MANAGER.renderModuleList) {
            m.renderDummy(mouseX, mouseY)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}