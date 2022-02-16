package com.evieclient.modules.impl.hypixel

import com.evieclient.events.bus.EventSubscriber
import com.evieclient.events.impl.client.ChatReceivedEvent
import com.evieclient.modules.Category
import com.evieclient.modules.Module
import net.minecraft.client.Minecraft

class AutoGG :
    Module("AutoGG", "Automatically says GG in the chat after a game on Hypixel.", Category.UTIL, true) {
    @EventSubscriber
    fun onGameEnd(event: ChatReceivedEvent?) {
        if (!Minecraft.getMinecraft().isSingleplayer) {
            if (event?.toString()?.matches(Regex("^ +1st Killer - ?\\[?\\w*\\+*\\]? \\w+ - \\d+(?: Kills?)?$")) == true) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("gg")
            }
            if (event?.toString()?.matches(Regex("^Match Details \\(click name to view\\)$")) == true) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("gg")
            }
        }
    }
}