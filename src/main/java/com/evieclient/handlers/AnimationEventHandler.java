package com.evieclient.handlers;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovingObjectPosition;

public class AnimationEventHandler {
    private final Minecraft mc;

    public AnimationEventHandler() {
        mc = Minecraft.getMinecraft();
    }


}

