package com.evieclient;

import com.evieclient.events.bus.EventBus;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.events.impl.client.input.KeyPressedEvent;
import com.evieclient.modules.ModuleManager;
import com.evieclient.modules.hud.HUDConfigScreen;
import com.evieclient.utils.api.SocketClient;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Evie {
    // Constants
    public static final String NAME = "Evie", VERSION = "b0.1", MCVERSION = "1.8.9";
    public static final File evieDir = new File(System.getenv("APPDATA") + "/." + NAME.toLowerCase());
    public static final File settingsFile = new File(System.getenv("APPDATA") + "/." + NAME.toLowerCase() + "/settings.json");
    public static final Evie INSTANCE = new Evie();
    public static final EventBus EVENT_BUS = new EventBus();
    public static String COMMIT_HASH = "unknown";
    public static Minecraft mc = Minecraft.getMinecraft();

    // Module Manager
    public static ModuleManager MODULE_MANAGER = null;

    static {
        try {
            MODULE_MANAGER = new ModuleManager();
        } catch (SlickException | IOException | FontFormatException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }
    }

    // States
    public boolean hasSentWS = false;

    // Main
    public Evie() {
        // Init Sentry
        Sentry.init(options -> {
            options.setDsn("https://24c61932d4a84df79726aee1615ae698@o1101223.ingest.sentry.io/6179220");
            options.setTracesSampleRate(1.0);
        });

        log("Starting Client!");

        if (!evieDir.exists()) {
            try {
                evieDir.mkdirs();
            } catch (Exception ignored) {
                Sentry.captureException(ignored);
            }
            log("Created EvieClient Directory: " + evieDir.getAbsolutePath());
        }
    }

    /**
     * Used to log messages to console.
     *
     * @param message any string to be displayed in console.
     **/
    public static void log(String... message) {
        for (String out : message)
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Evie] " + out);
    }

    /**
     * Used to log errors to console.
     *
     * @param message any string to be displayed in console.
     **/
    public static void error(String... message) {
        for (String out : message)
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Evie-Error] " + out);
    }

    // Post Launch
    public void postInitialisation() {
        log("Post Initiation Done! EV: " + COMMIT_HASH);
        MODULE_MANAGER.preInitialisation();
        Evie.EVENT_BUS.register(this);

        MODULE_MANAGER.reachDisplay.toggle();
    }

    // Build Info
    private void createBuildId() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/ci.txt");
        try {
            if (resourceAsStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
                COMMIT_HASH = br.readLine();
                br.close();
                resourceAsStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Websocket
    @EventSubscriber
    public void onTick(GameLoopEvent e) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            if (!hasSentWS) {
                // Register user on websocket
                SocketClient.registerUser(mc.thePlayer.getGameProfile().getName());
                // Register user on Sentry
                io.sentry.protocol.User user = new io.sentry.protocol.User();
                user.setId(mc.thePlayer.getGameProfile().getId().toString());
                user.setUsername(mc.thePlayer.getGameProfile().getName());
                Sentry.setUser(user);
                hasSentWS = true;
            }
        }
    }

    // Called when game shuts down.
    public void shutdown() {
        log("Shutting Down Client!");
        log("Shutdown " + MODULE_MANAGER.shutdown() + " modules!");
    }

    // Open HUD Config Screen
    @EventSubscriber
    public void onRshift(KeyPressedEvent e) {
        if (e.getKeyCode() == Keyboard.KEY_RSHIFT) {
            Minecraft.getMinecraft().displayGuiScreen(new HUDConfigScreen());
        }
    }

    // Test OldAnimations
    @EventSubscriber
    public void OnL(KeyPressedEvent e) {
        if (e.getKeyCode() == Keyboard.KEY_L) {
            Boolean enabled = MODULE_MANAGER.oldAnimations.toggle();
            if (enabled) {
                Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1.0F, 1.0F);
            } else {
                Minecraft.getMinecraft().thePlayer.playSound("note.snare", 1.0F, 1.0F);
            }
        }
    }

    @EventSubscriber
    public void Music(KeyPressedEvent e) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        if (e.getKeyCode() == Keyboard.KEY_LEFT) {
            Minecraft.getMinecraft().thePlayer.playSound("note.bd", 1.0F, 1.0F);
        }
        if (e.getKeyCode() == Keyboard.KEY_DOWN) {
            Minecraft.getMinecraft().thePlayer.playSound("note.snare", 1.0F, 1.0F);
        }
        if (e.getKeyCode() == Keyboard.KEY_RIGHT) {
            Minecraft.getMinecraft().thePlayer.playSound("note.hat", 1.0F, 1.0F);
        }
    }
}