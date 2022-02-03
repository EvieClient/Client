package com.evieclient.modules.impl.util;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.world.LoadWorldEvent;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.events.impl.client.GameLoopEvent;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class EvieAPIModule extends Module {

    public EvieAPIModule() {
        super("EvieAPI", "Allows the server and client to talk to each other via plugin messages", Category.UTIL, true);
    }

    @EventSubscriber
    public void onWorldLoad (LoadWorldEvent event) {
        if (!Minecraft.getMinecraft().isSingleplayer()) {
            // start receiving plugin messages
        }
    }

}