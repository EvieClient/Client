package com.evieclient.modules.impl.util

import com.evieclient.events.bus.EventSubscriber
import com.evieclient.events.impl.world.LoadWorldEvent
import com.evieclient.modules.Category
import com.evieclient.modules.Module
import net.minecraft.client.Minecraft

class EvieAPIModule :
    Module("EvieAPI", "Allows the server and client to talk to each other via plugin messages", Category.UTIL, true) {
    @EventSubscriber
    fun onWorldLoad(event: LoadWorldEvent?) {
        if (!Minecraft.getMinecraft().isSingleplayer) {
            // start receiving plugin messages

        }
    }
}