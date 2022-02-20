package com.evieclient.modules.impl.util;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.events.impl.world.LoadWorldEvent;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.modules.impl.improvements.OldAnimations;
import lombok.Getter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;

public class
DiscordRPModule extends Module {

    @Getter
    private boolean running = false;
    @Getter
    private long created = 0;
    public DiscordRPModule() {
        super("DiscordRP", "Updates Discord Rich Presents based on what your doing", Category.UTIL, true);
    }

    @Override
    public OldAnimations setupModule() {
        this.created = System.currentTimeMillis();
        Evie.log("Setting up DRPC");
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
                    Evie.log("Welcome " + user.username + "#" + user.discriminator + "!");
                    updateRP("loading..", "", "", "", null);
                }).setJoinGameEventHandler(JoinSecret -> {
                    // separate the secret from the IP
                    String[] split = JoinSecret.split("-");
                    String ip = split[0];
                    Evie.log("Joined server: " + ip);
                    // join the server
                    //Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(new NetHandlerPlayClient(Minecraft.getMinecraft().getSession().getProfile(), new ServerData(ip, "Evie Client", ip), Minecraft.getMinecraft().getCurrentServerData())));

                })
                .build();
        DiscordRPC.discordInitialize("938249807846322246", handlers, true);
        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
        return null;
    }

    @Override
    public void onDisable() {
        DiscordRPC.discordShutdown();
    }

    @Override
    public void shutdown() {
        DiscordRPC.discordShutdown();
    }

    @EventSubscriber
    public void onWorldLoad(LoadWorldEvent event) {
        if (!Minecraft.getMinecraft().isSingleplayer())
            this.updateRP("Multiplayer", "Server: " + Minecraft.getMinecraft().getCurrentServerData().serverIP, "evieclientlogo", "Evie Client", Minecraft.getMinecraft().getCurrentServerData());
        else this.updateRP("Singleplayer", "alone...", "evieclientlogo", "Evie Client", null);
    }

    @EventSubscriber
    public void onTick(GameLoopEvent event) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu)
            this.updateRP("Main Menu", "In Game", "evielogo", "Evie Client", null);
        else if (Minecraft.getMinecraft().currentScreen instanceof GuiMultiplayer)
            this.updateRP("Multiplayer Menu", "In Game", "evielogo", "Evie Client", null);
    }

    /**
     * Updates the Rich Presents
     *
     * @param line1     sets the text for the first line of the Rich Presents
     * @param line2     sets the text for the second line of the Rich Presents
     * @param image     sets a small image for the Rich Presents, leave blank if not needed
     * @param imageText sets text for the small image for the Rich Presents, leave blank if not needed
     **/
    public void updateRP(String line1, String line2, String image, String imageText, ServerData server) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(line2);
        b.setBigImage("evielogo", "Evie Client");
        if (image != null) {
            b.setSmallImage(image, imageText);
        }
        if (server != null) {
            b.setSecrets(server.serverIP + "-secret", "unused");
            b.setParty(server.serverIP, 1, 1000);
        }
        b.setDetails(line1);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
