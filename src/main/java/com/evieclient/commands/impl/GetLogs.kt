package com.evieclient.commands.impl

import com.evieclient.Evie
import com.evieclient.Evie.chatHandler
import com.evieclient.commands.BaseCommand
import com.evieclient.utils.chat.ChatColor
import com.evieclient.utils.saving.Save
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import java.util.*


class GetLogs : BaseCommand {
    override val name: String
        get() = "getlogs"
    override val usage: String
        get() = "/getlogs"

    override fun onExecute(args: Array<String>) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("${ChatColor.LIGHT_PURPLE}Evie Client Debug Logs:"))
        Evie.logs.forEach {
            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("${ChatColor.LIGHT_PURPLE}$it"))
        }
        Evie.logs.clear()
    }

    override val commandAliases: List<String>
        get() = Collections.singletonList("gl")
}

