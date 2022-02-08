package com.evieclient.mixins.client.gui;

import com.evieclient.Evie;
import com.evieclient.utils.render.EvieGuiScreen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiMainMenu.class)
public class MainMenu extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        EvieGuiScreen.renderBackgroundImage();
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        // credits
        mc.fontRendererObj.drawString("EvieClient Private Beta ("+ Evie.COMMIT_HASH+")", 0 + 3, this.height - 25, -1);
        mc.fontRendererObj.drawString("Copyright Mojang AB. Do not distribute!", 0 + 3, this.height - 15, -1);
    }

//    @Override
//    public void initGui() {
//        super.initGui();
//        this.buttonList.add(new GuiButton(1, width/4, (this.height / 2), "Singleplayer"));
//        this.buttonList.add(new GuiButton(2, width/4, (this.height / 2) + 25, "Multiplayer"));
//        this.buttonList.add(new GuiButton(3, width/4, (this.height / 2) + 50, "Settings"));
//       // this.buttonList.add(new GuiButton(4, 5, (this.height / 2) + 75, "Quit"));
//        // add a centered button
//        this.buttonList.add(new GuiButton(4, (width/4), (this.height / 2) + 100, "Quit"));
//    }
//
//    @Override
//    protected void actionPerformed(GuiButton button) throws IOException {
//        super.actionPerformed(button);
//        if(button.id == 1) {
//            Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(this));
//        }
//        if(button.id == 2) {
//            Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(this));
//        }
//        if(button.id == 3) {
//            Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(this, Minecraft.getMinecraft().gameSettings));
//        }
//        if(button.id == 4) {
//            Minecraft.getMinecraft().shutdown();
//        }
//    }
}
