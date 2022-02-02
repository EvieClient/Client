package com.evieclient.modules.impl.util;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.events.impl.world.LoadWorldEvent;
import lombok.Getter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;

public class DiscordRPModule extends Module {

    public DiscordRPModule () { super("DiscordRP", "Updates Discord Rich Presents based on what your doing", Category.UTIL, true); }

    @Getter private boolean running = false;
    @Getter private long created = 0;

    @Override public void setupModule () {
        this.created = System.currentTimeMillis();
        Evie.log("Setting up DRPC");
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            Evie.log("Welcome " + user.username + "#" + user.discriminator + "!");
            updateRP("loading..", "", "", "");
        }).build();
        DiscordRPC.discordInitialize("938249807846322246", handlers, true);
        new Thread("Discord RPC Callback") {
            @Override public void run () {
                while (running) { DiscordRPC.discordRunCallbacks(); }
            }
        }.start();
    }

    @Override public void onDisable () { DiscordRPC.discordShutdown(); }
    @Override public void shutdown () { DiscordRPC.discordShutdown(); }

    @EventSubscriber
    public void onWorldLoad (LoadWorldEvent event) {
        if (!Minecraft.getMinecraft().isSingleplayer())
            this.updateRP("Multiplayer", "Server: " + Minecraft.getMinecraft().getCurrentServerData().serverIP, "", "");
        else this.updateRP("Singleplayer", "vibing alone :(", "", "");
    }

    @EventSubscriber public void onTick (GameLoopEvent event) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu)
            this.updateRP("Main Menu", "In Game", "", "");
        else if (Minecraft.getMinecraft().currentScreen instanceof GuiMultiplayer)
            this.updateRP("Multiplayer Menu", "In Game", "", "");
    }

    /** Updates the Rich Presents
     * @param line1 sets the text for the first line of the Rich Presents
     * @param line2 sets the text for the second line of the Rich Presents
     * @param image sets a small image for the Rich Presents, leave blank if not needed
     * @param imageText sets text for the small image for the Rich Presents, leave blank if not needed **/
    public void updateRP (String line1, String line2, String image, String imageText) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(line2);
        b.setBigImage("evieclientlogo", "Evie Client");
        if (image != null) { b.setSmallImage(image, imageText); }
        b.setDetails(line1);
        b.setStartTimestamps(created);
        DiscordRPC.discordUpdatePresence(b.build());
    }
}
