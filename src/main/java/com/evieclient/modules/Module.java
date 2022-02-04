package com.evieclient.modules;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.settings.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Module constructor for all mods.
 *
 * @since 1.0.0
 **/
public class Module {

    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final Category category;
    @Getter
    private final ArrayList<Setting> settings = new ArrayList<>();
    public int xPos = 10;
    public int yPos = 10;
    @Setter
    private int priority = 5;
    @Setter
    @Getter
    private boolean enabled;
    public Module(String name, String description, Category category) {
        this(name, description, category, false);
    }

    /**
     * Main constructor used for all modules.
     *
     * @param name        name of module
     * @param description description of module
     * @param category    category of module
     * @param enabled     is module toggled
     **/
    public Module(String name, String description, Category category, boolean enabled) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = enabled;
        this.moduleSetup();
        if (this.isEnabled()) Evie.EVENT_BUS.register(this);
    }

    /**
     * Get module stats from file and log creation to console
     **/
    public final void moduleSetup() {
        Evie.log("Loading module: " + this.getName());
        this.setupModule();
    }

    /**
     * Set enabled state opposite of what it is currently.
     *
     * @return Boolean it was set too
     **/
    public final Boolean toggle() {
        this.setEnabled(!enabled);
        if (enabled) {
            this.onModuleEnable();
            return true;
        } else {
            this.onModuleDisable();
            return false;
        }
    }

    /**
     * Called when module is enabled and registers the event manager
     **/
    public final void onModuleEnable() {
        this.onEnabled();
    }

    /**
     * Called when module is disabled and unregisters the event manager
     **/
    public final void onModuleDisable() {
        this.onDisable();
    }

    /**
     * Called when module is enabled.
     *
     * @see #toggle()
     **/
    public void onEnabled() {
    }

    /**
     * Called when module is disabled.
     *
     * @see #toggle()
     **/
    public void onDisable() {
    }

    /**
     * Called on startup
     *
     * @see #moduleSetup()
     **/
    public void setupModule() {
    }

    /**
     * Called on Shutdown
     **/
    public void shutdown() {
    }
}
