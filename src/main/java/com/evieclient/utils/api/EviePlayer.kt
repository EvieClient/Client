package com.evieclient.utils.api

import com.evieclient.utils.misc.ThreadManager
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

class EviePlayer(name: String) {
    var cosmetics: PlayerCosmetics? = null
    val name: String = name

    init {
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            ChatComponentText("§a§l[§f§lEvie§a§l] §fMade a new EviePlayer Component for ${name}!")
        );
//        ThreadManager.runAsync(Runnable {
//            val cosmetics: PlayerCosmetics? = EvieRestAPI.getPlayerCosmetics(name)
//            this.cosmetics = cosmetics
//        })
    }

}
