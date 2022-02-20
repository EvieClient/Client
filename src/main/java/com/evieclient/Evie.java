package com.evieclient;

import com.evieclient.commands.CommandManager;
import com.evieclient.commands.EvieCommandHandler;
import com.evieclient.events.bus.EventBus;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.events.impl.client.PlayerChatEvent;
import com.evieclient.events.impl.client.input.KeyPressedEvent;
import com.evieclient.modules.ModuleManager;
import com.evieclient.modules.hud.HUDConfigScreen;
import com.evieclient.utils.api.Capes;
import com.evieclient.utils.api.EviePlayers;
import com.evieclient.utils.api.SocketClient;
import com.evieclient.utils.chat.ChatHandler;
import com.evieclient.utils.ui.UIUtils;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
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
    public static final Capes ECAPES = new Capes();
    public static String COMMIT_HASH = getCINote();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ChatHandler chatHandler = new ChatHandler();
    public static EvieCommandHandler commandHandler = new EvieCommandHandler();
    public static final UIUtils evieLogo = new UIUtils();

    // Module Manager
    public static ModuleManager MODULE_MANAGER = null;
    public static CommandManager COMMAND_MANAGER = new CommandManager();


    static {
        try {
            MODULE_MANAGER = new ModuleManager();
        } catch (SlickException | IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    // States
    public boolean hasSentWS = false;

    // Main
    public Evie() {
        log("Starting Client! EV: " + COMMIT_HASH);
        // Init Sentry
        Sentry.init(options -> {
            options.setDsn("https://24c61932d4a84df79726aee1615ae698@o1101223.ingest.sentry.io/6179220");
            options.setTracesSampleRate(1.0);
            options.setRelease(COMMIT_HASH);
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
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Evie/" + getCallerClassName() + "] " + out);
    }

    public static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Evie.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                return ste.getClassName();
            }
        }
        return null;
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

    // Build Info
    private static String getCINote() {
        InputStream resourceAsStream = Evie.class.getResourceAsStream("/ci.txt");
        try {
            if (resourceAsStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
                String line = br.readLine();
                br.close();
                resourceAsStream.close();
                return line;
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            return "???";
        }
        return "???";
    }

    // Post Launch
    public void postInitialisation() {
        MODULE_MANAGER.preInitialisation();
        Evie.EVENT_BUS.register(this);

        MODULE_MANAGER.reachDisplay.toggle();
        MODULE_MANAGER.keystrokes.toggle();
        MODULE_MANAGER.armorStatus.toggle();
        MODULE_MANAGER.cps.toggle();
        MODULE_MANAGER.autoGG.toggle();
    }

    @EventSubscriber
    public void onChat(PlayerChatEvent event){
        com.evieclient.Evie.commandHandler.onChat(event);
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
        if(Minecraft.getMinecraft().currentScreen == null && e.getKeyCode() == Keyboard.KEY_RSHIFT) {
            Minecraft.getMinecraft().displayGuiScreen(new HUDConfigScreen());
        }
    }

    // Test OldAnimations
    @EventSubscriber
    public void OnL(KeyPressedEvent e) {
        if (e.getKeyCode() == Keyboard.KEY_L) {
            boolean enabled = MODULE_MANAGER.oldAnimations.toggle();

            if (enabled) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§a§l[§f§lEvie§a§l] §fEnabled Old Animations!"));
            } else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§a§l[§f§lEvie§a§l] §fDisabled Old Animations!"));
            }
        }
    }
}