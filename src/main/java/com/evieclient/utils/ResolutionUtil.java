package com.evieclient.utils;

import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ResolutionUtil {
    private static ScaledResolution resolution;

    public static ScaledResolution current() {
        return resolution != null ? resolution : (resolution = new ScaledResolution(Minecraft.getMinecraft()));
    }

    @EventSubscriber
    public void onTick(GameLoopEvent event) {
        resolution = null;
    }
}

