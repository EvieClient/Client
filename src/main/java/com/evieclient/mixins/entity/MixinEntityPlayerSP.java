package com.evieclient.mixins.entity;

import com.evieclient.Evie;
import com.evieclient.events.impl.client.PlayerChatEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * MixinBootstrap Events for EntityPlayerSP.class.
 *
 * @since 1.0.0
 **/
@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;

    /**
     * Posts a {@link PlayerChatEvent}.
     * If the event is canceled, prevent the client from sending any message.
     *
     * @param message message player sent
     **/
    @Overwrite
    public void sendChatMessage(String message) {
        if (message != null) {
            PlayerChatEvent event = new PlayerChatEvent(message);
            event.post();
            if (!event.isCanceled()) {
                this.sendQueue.addToSendQueue(new C01PacketChatMessage(event.getMessage()));
            }
        }
    }
}
