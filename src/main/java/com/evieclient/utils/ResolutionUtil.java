package com.evieclient.utils;

import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ResolutionUtil {
    public static ScaledResolution current() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }
}

