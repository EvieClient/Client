package com.evieclient;

import com.evieclient.events.bus.EventBus;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.input.KeyPressedEvent;
import com.evieclient.modules.ModuleManager;
import io.sentry.Sentry;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Evie {
    // Constants
    public static final String NAME = "Evie", VERSION = "b0.1", MCVERSION = "1.8.9";
    public static final File evieDir = new File(System.getenv("APPDATA") + "/." + NAME.toLowerCase());
    public static final File settingsFile = new File(System.getenv("APPDATA") + "/." + NAME.toLowerCase() + "/settings.json");

    // Public client instance
    public static final Evie INSTANCE = new Evie();
    public static final EventBus EVENT_BUS = new EventBus();
    public static ModuleManager MODULE_MANAGER = null;

    static {
        try {
            MODULE_MANAGER = new ModuleManager();
        } catch (SlickException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        } catch (IOException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        } catch (FontFormatException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }
    }

    // Main
    public Evie() {
        // Init Sentry
        Sentry.init(options -> {
            options.setDsn("https://24c61932d4a84df79726aee1615ae698@o1101223.ingest.sentry.io/6179220");
            options.setTracesSampleRate(1.0);
        });

        log("Starting Client!");

        if (!evieDir.exists()) {
            try { evieDir.mkdirs();  } catch (Exception ignored) {
                Sentry.captureException(ignored);
            }
            log("Created EvieClient Directory: " + evieDir.getAbsolutePath());
        }
    }

    // Post Launch
    public void postInitialisation() {
        log("Post Initiation Done! ");
        MODULE_MANAGER.preInitialisation();
        Evie.EVENT_BUS.register(this);
    }

    // Called when game shuts down.
    public void shutdown() {
        log("Shutting Down Client!");
        log("Shutdown " + MODULE_MANAGER.shutdown() + " modules!");
    }

    @EventSubscriber
    public void onRshift(KeyPressedEvent e){
        if(e.getKeyCode() == Keyboard.KEY_RSHIFT){
            MODULE_MANAGER.reachDisplay.toggle();
        }
    }

    /** Used to log messages to console.
     * @param message any string to be displayed in console. **/
    public static void log (String... message) { for (String out : message) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Evie] " + out); }

    /** Used to log errors to console.
     * @param message any string to be displayed in console. **/
    public static void error (String... message) { for (String out : message) System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Evie-Error] " + out); }
}