package com.evieclient.commands

import net.minecraft.command.CommandException
import java.util.*


/**
 * The basic command implementation
 */
interface BaseCommand {
    /**
     * Gets the name of the command
     */
    val name: String?

    /**
     * Gets the usage string for the command.
     */
    val usage: String?

    /**
     * A list of aliases to the main command
     * this will not be used if null/empty
     */
    val commandAliases: List<String?>?
        get() = ArrayList()

    /**
     * Callback when the command is invoked
     *
     * @throws CommandException for errors inside the command, these errors
     * will log directly to the players chat (without a prefix)
     */
    @Throws(CommandException::class)
    fun onExecute(args: Array<String>)

    /**
     * Called when the player clicks tab in the chat menu, used to provide suggestions for a commands arguments
     *
     * @param args the arguments the player has entered
     * @return a String List containing all viable tab completions
     */
    fun onTabComplete(args: Array<String?>?): List<String?>? {
        return null
    }

    /**
     * Tells the command handler not to register the command, and to use [.onTabComplete]
     *
     * @return true if this command should not be executed
     */
    fun tabOnly(): Boolean {
        return false
    }
}
