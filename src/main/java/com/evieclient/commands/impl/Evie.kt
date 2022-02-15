package com.evieclient.commands.impl

import com.evieclient.commands.BaseCommand
import com.evieclient.modules.hud.HUDConfigScreen
import net.minecraft.client.Minecraft
import java.util.*


class Evie : BaseCommand {
    override val name: String
        get() = "evie"
    override val usage: String
        get() = "/evie"

    override fun onExecute(args: Array<String>) {
        Minecraft.getMinecraft().displayGuiScreen(HUDConfigScreen())
    }

    override val commandAliases: List<String>
        get() = Collections.singletonList("ev")
}

