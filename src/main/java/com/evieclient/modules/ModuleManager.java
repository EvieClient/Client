

package com.evieclient.modules;

import com.evieclient.modules.hud.RenderModule;
import com.evieclient.modules.impl.display.ReachDisplay;
import com.evieclient.modules.impl.util.AccountsModule;
import com.evieclient.modules.impl.util.DiscordRPModule;
import com.evieclient.modules.impl.util.WindowedFullscreenModule;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/** Holds all modules and sets up module settings.
 * @since 1.0.0 **/
public class ModuleManager {

    // Settings file used to save setting states.
    public final ArrayList<Module> modules = new ArrayList<>();

    // Public module instances.
    public final DiscordRPModule discordRPModule = new DiscordRPModule();
    public final AccountsModule accountsModule = new AccountsModule();
    public final WindowedFullscreenModule windowedFullscreenModule = new WindowedFullscreenModule();
    public final ReachDisplay reachDisplay = new ReachDisplay();

    // Add all modules here.
    public final void preInitialisation() {
        this.modules.add(discordRPModule);
        this.modules.add(windowedFullscreenModule);
        this.modules.add(accountsModule);
        this.modules.add(reachDisplay);
    }

    /** Constructor to created ModuleManager. **/
    public ModuleManager() throws SlickException, IOException, FontFormatException {
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

    public ArrayList<RenderModule> getRenderModuleList() {
        ArrayList<RenderModule> renderModules = new ArrayList<>();
        for(Module module : modules) {
            if(module instanceof RenderModule) {
                RenderModule m = (RenderModule) module;
                renderModules.add(m);
            }
        }
        return renderModules;
    }

    public void renderHook() {
        for(RenderModule m : getRenderModuleList()) {
            if (m.isEnabled()) {
                m.render();
            }
        }
    }
}
