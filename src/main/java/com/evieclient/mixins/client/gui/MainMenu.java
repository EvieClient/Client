package com.evieclient.mixins.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

import java.io.IOException;

@Mixin(GuiMainMenu.class) public class MainMenu extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("evieclient/bg.png"));
        this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        // credits
        mc.fontRendererObj.drawString("EvieClient Since 2022" ,0 + 3, this.height - 15, -1);
        mc.fontRendererObj.drawString("Not affiliated with Mojang" ,0 + 3, this.height - 5, -1);

        GlStateManager.pushMatrix();
        GlStateManager.translate(width/2f, height/2f, 0);
        GlStateManager.scale(3, 3, 1);
        GlStateManager.translate(-(width/2f), -(height/2f), 0);
        this.drawCenteredString(mc.fontRendererObj, "EvieClient", (int) width/2, height/2 - mc.fontRendererObj.FONT_HEIGHT/2, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, 5, (this.height / 2), "Singleplayer"));
        this.buttonList.add(new GuiButton(2, 5, (this.height / 2) + 25, "Multiplayer"));
        this.buttonList.add(new GuiButton(3, 5, (this.height / 2) + 50, "Settings"));
       // this.buttonList.add(new GuiButton(4, 5, (this.height / 2) + 75, "Quit"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 1) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(this));
        }
        if(button.id == 2) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(this));
        }
        if(button.id == 3) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(this, Minecraft.getMinecraft().gameSettings));
        }
        if(button.id == 4) {
            Minecraft.getMinecraft().shutdown();
        }
    }
}
