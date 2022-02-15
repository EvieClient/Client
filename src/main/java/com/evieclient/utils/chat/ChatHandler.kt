package com.evieclient.utils.chat

import com.evieclient.events.bus.EventSubscriber
import com.evieclient.events.impl.client.GameLoopEvent
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import java.util.concurrent.ConcurrentLinkedQueue


class ChatHandler {
    private val messages = ConcurrentLinkedQueue<IChatComponent>()

    /**
     * Queues a [IChatComponent] to send to the player.
     *
     * @param component the Chat Component to send.
     */
    fun sendMessage(text: String) {
        messages.add(ChatComponentText(text))
    }

    @EventSubscriber
    fun tick(event: GameLoopEvent) {
        if (Minecraft.getMinecraft().thePlayer == null) return
        while (!messages.isEmpty()) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(messages.poll())
        }
    }
}