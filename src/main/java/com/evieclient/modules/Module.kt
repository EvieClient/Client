package com.evieclient.modules

import com.evieclient.Evie
import kotlin.jvm.JvmOverloads
import com.evieclient.modules.impl.improvements.OldAnimations
import com.evieclient.settings.Setting
import lombok.Getter
import lombok.Setter
import java.util.ArrayList

/**
 * Module constructor for all mods.
 *
 * @since 1.0.0
 */
open class Module @JvmOverloads constructor(
    @Getter val name: String,
    @Getter val description: String,
    @Getter val category: Category,
    @Getter var enabled: Boolean = false
) {
    private val settings = ArrayList<Setting>()

    @Setter
    private val priority = 5

    /**
     * Main constructor used for all modules.
     *
     * @param name        name of module
     * @param description description of module
     * @param category    category of module
     * @param enabled     is module toggled
     */
    init {
        moduleSetup()
        if (this.enabled) Evie.EVENT_BUS.register(this)
    }

    /**
     * Get module stats from file and log creation to console
     */
    fun moduleSetup() {
        Evie.log("Loading module: " + this.name)
        setupModule()
    }

    /**
     * Set enabled state opposite of what it is currently.
     *
     * @return Boolean it was set too
     */
    fun toggle(): Boolean {
        this.enabled = !enabled
        return if (enabled) {
            onModuleEnable()
            true
        } else {
            onModuleDisable()
            false
        }
    }

    /**
     * Called when module is enabled and registers the event manager
     */
    fun onModuleEnable() {
        onEnabled()
    }

    /**
     * Called when module is disabled and unregisters the event manager
     */
    fun onModuleDisable() {
        onDisable()
    }

    open fun isRenderModule(): Boolean {
        return false
    }

    /**
     * Called when module is enabled.
     *
     * @see .toggle
     */
    open fun onEnabled() {}

    /**
     * Called when module is disabled.
     *
     * @see .toggle
     */
    open fun onDisable() {}
    /**
     * Called on startup
     *
     * @see .moduleSetup
     * @return*/

    open fun setupModule() {
        return;
    }

    /**
     * Called on Shutdown
     */
    open fun shutdown() {}
}