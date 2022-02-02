

package com.evieclient.modules;

import com.evieclient.modules.impl.util.DiscordRPModule;
import com.evieclient.modules.impl.util.WindowedFullscreenModule;

import java.util.ArrayList;

/** Holds all modules and sets up module settings.
 * @author Icovid | Icovid#3888
 * @since 1.0.0 **/
public class ModuleManager {

    // Settings file used to save setting states.
    public final ArrayList<Module> modules = new ArrayList<>();

    // Public module instances.
    public final DiscordRPModule discordRPModule = new DiscordRPModule();
    public final WindowedFullscreenModule windowedFullscreenModule = new WindowedFullscreenModule();

    // Add all modules here.
    public final void preInitialisation() {
        this.modules.add(discordRPModule);
        this.modules.add(windowedFullscreenModule);
    }

    /** Constructor to created ModuleManager. **/
    public ModuleManager() {
        this.preInitialisation();
    }

    /** Called when game is closed.
     * <p>used to shutdown modules</p>
     * @return number of modules shutdown. **/
    public int shutdown() {
        for (Module module : this.modules)
            module.shutdown();
        return this.modules.size();
    }
}
