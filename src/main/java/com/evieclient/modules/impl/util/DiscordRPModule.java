package com.evieclient.modules.impl.util;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.events.impl.world.LoadWorldEvent;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.utils.api.DownloadNativeLibrary;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import io.sentry.Sentry;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;

import java.io.File;
import java.time.Instant;

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
    public void setupModule() {
        try {
            File discordLibrary = DownloadNativeLibrary.downloadDiscordLibrary();
            if (discordLibrary == null) {
                System.err.println("Error downloading Discord SDK.");
                System.exit(-1);
            }
            // Initialize the Core
            Core.init(discordLibrary);
        } catch (Exception e) {
            Sentry.captureException(e);
        }

        // Set parameters for the Core
        try(CreateParams params = new CreateParams())
        {
            params.setClientID(698611073133051974L);
            params.setFlags(CreateParams.getDefaultFlags());
            // Create the Core
            try(Core core = new Core(params))
            {
                // Create the Activity
                try(Activity activity = new Activity())
                {
                    activity.setDetails("Running an example");
                    activity.setState("and having fun");

                    // Setting a start time causes an "elapsed" field to appear
                    activity.timestamps().setStart(Instant.now());

                    // We are in a party with 10 out of 100 people.
                    activity.party().size().setMaxSize(100);
                    activity.party().size().setCurrentSize(10);

                    // Make a "cool" image show up
                    activity.assets().setLargeImage("test");

                    // Setting a join secret and a party ID causes an "Ask to Join" button to appear
                    activity.party().setID("Party!");
                    activity.secrets().setJoinSecret("Join!");

                    // Finally, update the current activity to our activity
                    core.activityManager().updateActivity(activity);
                }

                // Run callbacks forever
                while(true)
                {
                    core.runCallbacks();
                    try
                    {
                        // Sleep a bit to save CPU
                        Thread.sleep(16);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
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
