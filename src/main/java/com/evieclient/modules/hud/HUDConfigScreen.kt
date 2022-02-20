package com.evieclient.modules.hud

import com.evieclient.Evie
import com.evieclient.utils.saving.Save
import com.evieclient.utils.shader.GLRenderer
import com.evieclient.utils.ui.UIUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import java.awt.Color

class HUDConfigScreen : GuiScreen() {

    private val evieLogo = UIUtils()

    override fun onGuiClosed() {
        Save.saveConfig()
        super.onGuiClosed()
    }

    override fun initGui() {
        super.initGui()
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
    }


    override fun actionPerformed(button: GuiButton?) {
        super.actionPerformed(button);
        if (button != null) {
            if(button.id == 0) {
                Minecraft.getMinecraft().displayGuiScreen(EvieConfigScreen());
            }
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Evie.evieLogo.drawPinkBars(this.width, this.height)
        evieLogo.drawEvieLogo(this.width / 2 - 50, this.height / 2 - 50, 100)

        // drawDefaultBackground()

        for (m in Evie.MODULE_MANAGER.renderModuleList) {
            m.renderDummy(mouseX, mouseY)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}