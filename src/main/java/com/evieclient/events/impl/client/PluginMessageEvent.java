package com.evieclient.events.impl.client;

import com.evieclient.Evie;
import com.evieclient.events.CancelableEvent;
import lombok.Getter;
import net.minecraft.network.PacketBuffer;

/**
 * Fired when the client receives a PluginMessage
 *
 **/
public class PluginMessageEvent extends CancelableEvent {

    @Getter
    private final String channel;

    @Getter
    private final PacketBuffer data;

    /**
     * @param channel The channel the message was sent on
     * @param packet The data sent
     **/
    public PluginMessageEvent(String channel, PacketBuffer packet) {
        this.channel = channel;
        this.data = packet;

        Evie.log("Found plugin message: " + channel + " with msg of " + packet.toString());
    }
}
