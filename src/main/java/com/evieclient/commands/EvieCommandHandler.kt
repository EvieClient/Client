package com.evieclient.commands

import com.evieclient.Evie
import com.evieclient.events.bus.EventSubscriber
import com.evieclient.events.impl.client.PlayerChatEvent
import com.evieclient.utils.chat.ChatColor
import com.evieclient.utils.chat.ChatHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiChat
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap


/**
 * This is our custom client-side command implementation, it handles most of the
 * command logic and other firing methods. Commands should be registered by doing
 * *Evie.commandHandler.registerCommand([BaseCommand])*
 */
class EvieCommandHandler {
    // If a command is in this
    private val disabledCommands: MutableSet<String> = TreeSet(java.lang.String.CASE_INSENSITIVE_ORDER)
    private val commands: MutableMap<String, BaseCommand> = HashMap<String, BaseCommand>()
    private val mc: Minecraft = Minecraft.getMinecraft()
    private lateinit var latestAutoComplete: Array<String>
    private val chatHandler: ChatHandler = Evie.chatHandler;

    private var runningCommand = false


    fun onChat(event: PlayerChatEvent) {
        val chatLine: String = event.message
        // Attempt to execute command if necessary
        if (chatLine.startsWith("/") && chatLine.length > 1 && executeCommand(chatLine)) {
            // It is one of our commands, we'll cancel the event, so it isn't
            // sent to the server, and we'll close the currently opened gui
            event.cancel()
            if (runningCommand) {
                mc.displayGuiScreen(null)
                runningCommand = false
            }
        }
    }

    /**
     * Execute the provided command, if it exists. Initial leading slash will be removed if it is sent.
     *
     * @param command Command to attempt to execute
     * @return Whether the command was successfully executed
     */
    fun executeCommand(command: String): Boolean {
        val commandLine = if (command.startsWith("/")) command.substring(1) else command
        val commandName: String
        var args = arrayOf<String>()

        // Check if arguments are provided.
        if (commandLine.contains(" ")) {
            val syntax = commandLine.split(" ").toTypedArray()
            commandName = syntax[0].toLowerCase()
            args = Arrays.copyOfRange(syntax, 1, syntax.size)
        } else {
            commandName = commandLine.toLowerCase()
        }

        // Disabled commands will be ignored
        if (isCommandDisabled(commandName)) return false

        // Loop through our commands, if the identifier matches the expected command, active the base
        val finalArgs = args
        return commands.entries.stream()
            .filter { (key, value): Map.Entry<String, BaseCommand> -> commandName == key && !value.tabOnly() }
            .findFirst().map { (_, baseCommand): Map.Entry<String, BaseCommand> ->
                try {
                    baseCommand.onExecute(finalArgs)
                } catch (usageEx: CommandUsageException) {
                    // Throw a UsageException to trigger
                    chatHandler.sendMessage("${ChatColor.RED}$baseCommand.getUsage()")
                } catch (knownEx: CommandException) {
                    if (knownEx.message != null) {
                        chatHandler.sendMessage("${ChatColor.LIGHT_PURPLE}$knownEx.message")
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    chatHandler.sendMessage(
                        "${ChatColor.RED}DAn internal error occurred whilst performing this command")
                    return@map false
                }
                true
            }.orElse(false)
    }

    /**
     * Registers the command to this CommandHandler instance.
     * also registers any aliases if applicable
     *
     * @param command The command to register
     */
    fun registerCommand(command: BaseCommand) {
        commands[command.name!!] = command
        if (command.commandAliases != null && command.commandAliases!!.isNotEmpty()) {
            command.commandAliases!!.forEach { alias ->
                if (alias != null) {
                    commands[alias] = command
                }
            }
        }
    }

    /**
     * Removes a register command & all aliases
     *
     * @param command the command to unregister
     */
    fun removeCommand(command: BaseCommand?) {
        commands.forEach { (key: String?, value: BaseCommand) ->
            if (value.equals(command)) {
                commands.remove(key)
            }
        }
    }

    /**
     * Returns true if this command is in the disabled list. Used to ignore commands
     *
     * @param input the command to check
     * @return true if the command should be ignored
     */
    fun isCommandDisabled(input: String?): Boolean {
        return input != null && !input.isEmpty() && !input.trim { it <= ' ' }.isEmpty() &&
                !input.equals("disablecommand", ignoreCase = true) && !input.equals(
            "evie",
            ignoreCase = true
        ) && disabledCommands.contains(input.trim { it <= ' ' })
    }

    /**
     * If this command is already disabled, we'll remove it from the disabled list
     * and return false to indicate the command is not disabled anymore. If the list
     * does not contain the command we'll add it and return true to indicate it now is.
     *
     * @param input the command to add to the ignored list
     * @return true if now disabled or false if it no longer is
     */
    fun addOrRemoveCommand(input: String?): Boolean {
        var input = input
        if (input == null || input.isEmpty() || input.trim { it <= ' ' }.isEmpty() ||
            input.equals("disablecommand", ignoreCase = true) || input.equals("hyperium", ignoreCase = true)
        ) {
            return false
        }
        input = input.trim { it <= ' ' }
        return if (isCommandDisabled(input)) {
            disabledCommands.remove(input)
            false
        } else {
            disabledCommands.add(input)
            true
        }
    }

    /**
     * @author Forge
     */
    fun autoComplete(leftOfCursor: String) {
        var leftOfCursor = leftOfCursor
        latestAutoComplete = emptyArray()
        if (leftOfCursor.isEmpty()) return
        if (leftOfCursor[0] == '/') {
            leftOfCursor = leftOfCursor.substring(1)
            if (mc.currentScreen is GuiChat) {
                val completions = getTabCompletionOptions(leftOfCursor)?.toMutableList() as MutableList<String>?
                if (completions != null && completions.isNotEmpty()) {
                    if (leftOfCursor.indexOf(' ') == -1) {
                        val bound = completions.size
                        for (i in 0 until bound) {
                            completions[i   ] = "${ChatColor.GRAY}/${completions[i]}${ChatColor.RESET}"
                        }
                    }
                    completions.sort()
                    latestAutoComplete = completions.toTypedArray()
                }
            }
        }
    }

    /**
     * @author Forge
     */
    private fun getTabCompletionOptions(input: String): List<String?>? {
        val astring = input.split(" ").dropLastWhile { it.isEmpty() }.toTypedArray()
        val s = astring[0]
        return if (astring.size == 1) {
            commands.keys.stream().filter { baseCommand: String? ->
                CommandBase.doesStringStartWith(
                    s,
                    baseCommand
                )
            }.collect(Collectors.toList())
        } else {
            commands[s]?.onTabComplete(dropFirstString(astring))
        }
    }

    /**
     * @author Forge
     */
    private fun dropFirstString(input: Array<String>): Array<String?> {
        val astring = arrayOfNulls<String>(input.size - 1)
        System.arraycopy(input, 1, astring, 0, input.size - 1)
        return astring
    }

    fun clear() {
        commands.clear()
    }

    fun getCommands(): Map<String, BaseCommand> {
        return commands
    }
}