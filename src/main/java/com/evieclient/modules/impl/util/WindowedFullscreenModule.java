package com.evieclient.modules.impl.util;

import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class WindowedFullscreenModule extends Module {
    private boolean lastFullscreen;

    public WindowedFullscreenModule() {
        super("WindowedFullscreen", "Lets you use multiple monitors without going out of the Fullscreen", Category.UTIL, true);
    }

    @Override
    public void onEnabled() {
    }

    @EventSubscriber
    public void onTick(GameLoopEvent event) {
        boolean fullScreenNow = Minecraft.getMinecraft().isFullScreen();
        if (this.lastFullscreen != fullScreenNow) {
            this.fullScreen(fullScreenNow);
            this.lastFullscreen = fullScreenNow;
        }
    }

    public void fullScreen(boolean fullscreen) {
        try {
            if (fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);
                Display.setFullscreen(false);
                Display.setResizable(false);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight));
                Display.setResizable(true);
            }
        } catch (LWJGLException lwjglexception) {
            Sentry.captureException(lwjglexception);
            lwjglexception.printStackTrace();
        }
    }
}