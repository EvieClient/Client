package com.evieclient.modules.hud;

import com.evieclient.Evie;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

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
            customFr = new com.evieclient.utils.render.FontRenderer("ROBOTO", 10);
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        if (this.isEnabled()) Evie.EVENT_BUS.register(this);
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

    public void render() {
    }

    public void renderDummy(int cursorX, int cursorY) {
        drag.draw(cursorX, cursorY);
    }
}
