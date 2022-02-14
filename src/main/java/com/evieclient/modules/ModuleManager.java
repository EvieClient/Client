package com.evieclient.modules;

import com.evieclient.modules.hud.RenderModule;
import com.evieclient.modules.hud.impl.info.ArmorStatus;
import com.evieclient.modules.hud.impl.info.CPS;
import com.evieclient.modules.hud.impl.info.Keystrokes;
import com.evieclient.modules.hud.impl.info.ReachDisplay;
import com.evieclient.modules.impl.hypixel.AutoGG;
import com.evieclient.modules.impl.improvements.OldAnimations;
import com.evieclient.modules.impl.util.AccountsModule;
import com.evieclient.modules.impl.util.DiscordRPModule;
import com.evieclient.modules.impl.util.WindowedFullscreenModule;
import org.checkerframework.checker.units.qual.K;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Holds all modules and sets up module settings.
 *
 * @since 1.0.0
 **/
public class ModuleManager {

    public final OldAnimations oldAnimations = new OldAnimations();
    // Settings file used to save setting states.
    public final ArrayList<Module> modules = new ArrayList<>();
    // Public module instances.
    public final DiscordRPModule discordRPModule = new DiscordRPModule();
    public final AccountsModule accountsModule = new AccountsModule();
    public final WindowedFullscreenModule windowedFullscreenModule = new WindowedFullscreenModule();
    public final AutoGG autoGG = new AutoGG();
    // Display Modules
    public final ReachDisplay reachDisplay = new ReachDisplay();
    public final Keystrokes keystrokes = new Keystrokes();
    public final ArmorStatus armorStatus = new ArmorStatus();
    public final CPS cps = new CPS();

    /**
     * Constructor to created ModuleManager.
     **/
    public ModuleManager() throws SlickException, IOException, FontFormatException {
        this.preInitialisation();
    }

    // Add all modules here.
    public final void preInitialisation() {
        this.modules.add(discordRPModule);
        this.modules.add(windowedFullscreenModule);
        this.modules.add(accountsModule);
        this.modules.add(oldAnimations);
        this.modules.add(autoGG);
        // Display Modules //
        this.modules.add(reachDisplay);
        this.modules.add(keystrokes);
        this.modules.add(armorStatus);
        this.modules.add(cps);
    }

    /**
     * Called when game is closed.
     * <p>used to shutdown modules</p>
     *
     * @return number of modules shutdown.
     **/
    public int shutdown() {
        for (Module module : this.modules)
            module.shutdown();
        return this.modules.size();
    }

    public ArrayList<RenderModule> getRenderModuleList() {
        ArrayList<RenderModule> renderModules = new ArrayList<>();
        for (Module module : modules) {
            if (module instanceof RenderModule) {
                RenderModule m = (RenderModule) module;
                renderModules.add(m);
            }
        }
        return renderModules;
    }

    public void renderHook() {
        for (RenderModule m : getRenderModuleList()) {
            if (m.isEnabled()) {
                m.render();
            }
        }
    }
}
