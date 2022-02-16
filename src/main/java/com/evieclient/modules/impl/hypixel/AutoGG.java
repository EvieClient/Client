package com.evieclient.modules.impl.hypixel;

import com.evieclient.Evie;
import com.evieclient.events.bus.EventSubscriber;
import com.evieclient.events.impl.client.ChatReceivedEvent;
import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import kotlin.text.Regex;
import net.minecraft.client.Minecraft;

public class AutoGG extends Module

public AutoGG() {
        super("AutoGG", "Automatically says GG in the chat after a game on Hypixel.", Category.UTIL, true);
        }
    @EventSubscriber
    public void onGameEnd(ChatReceivedEvent event) {
        if (!Minecraft.getMinecraft().isSingleplayer()) {
            Evie.log("Found ChatReceivedEvent, checking Regex");

            if(event.getChatComponent().getUnformattedText().matches("^\\+1st Killer - ?\\[?\\w*\\+*\\]? \\w+ - \\d+(?: Kills?)?$")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("gg");
            }
            if (event.getChatComponent().getUnformattedText().matches("^Match Details \\(click name to view\\)$")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("gg");
            }
        }
    }
}