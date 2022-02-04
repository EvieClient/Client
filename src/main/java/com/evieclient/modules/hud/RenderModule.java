package com.evieclient.modules.hud;

import com.evieclient.Evie;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderModule extends Module {

    public Minecraft mc = Minecraft.getMinecraft();
    public FontRenderer fr;
    public com.evieclient.utils.render.FontRenderer customFr;
    public int xPos = 0;
    public int yPos = 0;

    public RenderModule(String name, String description, Category category, Boolean enabled) {
        super(name, description, category, false);
        fr = mc.fontRendererObj;
        try {
            customFr = new com.evieclient.utils.render.FontRenderer("ROBOTO", 10);
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        if (this.isEnabled()) Evie.EVENT_BUS.register(this);
    }

    public RenderModule(String name, String description, Category category, int defaultX, int defaultY) {
        super(name,description, category);
        fr = mc.fontRendererObj;
        try {
            customFr = new com.evieclient.utils.render.FontRenderer("ROBOTO", 10);
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        this.xPos = defaultX;
        this.yPos = defaultY;
        if (this.isEnabled()) Evie.EVENT_BUS.register(this);
        this.setEnabled(true);
    }

    public boolean isHovered(int mouseX, int mouseY) {

        return mouseX >= xPos && mouseX <= xPos + getWidth() && mouseY >= yPos && mouseY <= yPos + getHeight();

    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setX(int x) {
        this.xPos = x;
    }

    public void setY(int y) {
        this.yPos = y;
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public void render() {

    }

    public void renderDummy() {

    }
}
