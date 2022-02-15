package com.evieclient.commands

import com.evieclient.commands.impl.Evie
import com.evieclient.modules.Module
import com.evieclient.modules.impl.improvements.OldAnimations
import com.evieclient.modules.impl.util.DiscordRPModule
import com.evieclient.modules.impl.util.AccountsModule
import com.evieclient.modules.impl.util.WindowedFullscreenModule
import com.evieclient.modules.impl.hypixel.AutoGG
import com.evieclient.modules.hud.impl.info.ReachDisplay
import com.evieclient.modules.hud.impl.info.Keystrokes
import com.evieclient.modules.hud.impl.info.ArmorStatus
import com.evieclient.modules.hud.impl.info.CPS
import com.evieclient.modules.hud.RenderModule
import java.util.ArrayList

/**
 * Holds all commands and registers commands.
 *
 * @since 1.0.0
 */
class CommandManager {
    @JvmField

    // Settings file used to save setting states.
    val commands = ArrayList<BaseCommand>()
    val evie = Evie()


    /**
     * Constructor to created ModuleManager.
     */
    init {
        preInitialisation()
        for (command in commands) {
            com.evieclient.Evie.log("Registered Command: ${command.name}")
            com.evieclient.Evie.commandHandler.registerCommand(command)
        }
    }

    // Add all modules here.
    private fun preInitialisation() {
        commands.add(evie);
    }
}