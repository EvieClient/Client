package com.evieclient.mixins.client.gui;

import com.evieclient.Evie;
import com.evieclient.utils.render.EvieGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;

import java.io.IOException;

@Mixin(GuiMainMenu.class)
public abstract class MainMenu extends GuiScreen implements GuiYesNoCallback {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        EvieGuiScreen.renderBackgroundImage();
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        // credits
        mc.fontRendererObj.drawString("EvieClient Private Beta", 0 + 3, this.height - 25, -1);
        mc.fontRendererObj.drawString("Copyright Mojang AB. Do not distribute!", 0 + 3, this.height - 15, -1);

        //        x: Int,
        //        y: Int,
        //        width: Int

        // using  Evie.evieLogo.draw(x, y, width);
        int logoWidth = buttonList.get(0).getButtonWidth() / 2;
        Evie.evieLogo.draw(this.width / 2 - logoWidth / 2, this.height / 6 - logoWidth / 2, logoWidth);


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();

        // centred buttons using vanilla code
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "1player + lan if you still do that"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, "World-Wide-Web"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96, "Windows Control Panel"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120, "Evie.stopClient(this)"));
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