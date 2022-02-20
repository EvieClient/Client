package com.evieclient.modules.hud;

import com.evieclient.Evie;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class RenderModule extends Module {

    public Minecraft mc = Minecraft.getMinecraft();
    public FontRenderer fr;
    public com.evieclient.utils.render.FontRenderer customFr;

    public DraggableComponent drag;

    public RenderModule(String name, String description, Category category, Boolean enabled) {
        super(name, description, category, false);
        fr = mc.fontRendererObj;
        drag = new DraggableComponent(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0).getRGB());
        try {
            customFr = new com.evieclient.utils.render.FontRenderer("fonts/Roboto-Thin.ttf", 10);
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        if (this.getEnabled()) Evie.EVENT_BUS.register(this);
    }

    @Override
    public boolean isRenderModule() {
        return true;
    }

    public int getX() {
        return drag.getxPosition();
    }

    public int getY() {
        return drag.getyPosition();
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public void renderModule() {
    }

    public void render(){
        // make a background box with a 3 pixel border
        Gui.drawRect(
                getX() - 3,
                getY() - 3,
                getX() + getWidth() + 3,
                getY() + getHeight() + 3,
         new Color(0, 0, 0, 75).getRGB()
            );
        renderModule();
    }


    public void renderDummy(int cursorX, int cursorY) {
        drag.draw(cursorX, cursorY);
    }
}
