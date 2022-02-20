package com.evieclient.commands.impl

import com.evieclient.Evie
import com.evieclient.Evie.chatHandler
import com.evieclient.commands.BaseCommand
import com.evieclient.utils.chat.ChatColor
import com.evieclient.utils.saving.Save
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import java.util.*


class Evie : BaseCommand {
    override val name: String
        get() = "evie"
    override val usage: String
        get() = "/evie"

    override fun onExecute(args: Array<String>) {
        Evie.log("executing evie command")
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Save.loadConfig().toString()))

        chatHandler.sendMessage("${ChatColor.LIGHT_PURPLE}Evie Client (${com.evieclient.Evie.COMMIT_HASH}) by ${ChatColor.BOLD}Team Evie")
    }

    override val commandAliases: List<String>
        get() = Collections.singletonList("ev")
}

