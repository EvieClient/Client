package com.evieclient.modules.hud;

import com.evieclient.Evie;
import net.minecraft.client.gui.GuiScreen;

public class HUDConfigScreen extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        for (RenderModule m : Evie.MODULE_MANAGER.getRenderModuleList()) {
            m.renderDummy(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
