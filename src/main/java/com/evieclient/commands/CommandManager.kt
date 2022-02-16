package com.evieclient.commands

import com.evieclient.commands.impl.Evie
import java.util.*

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