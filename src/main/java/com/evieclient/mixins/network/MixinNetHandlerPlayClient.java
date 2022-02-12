package com.evieclient.mixins.network;

import com.evieclient.events.impl.client.ActionBarEvent;
import com.evieclient.events.impl.client.ChatReceivedEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinBootstrap Events for MixinNetHandlerPlayClient.class.
 *
 * @since 1.0.0
 **/
@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    /**
     * Post {@link ActionBarEvent} or {@link ChatReceivedEvent} at chat packet.
     *
     * @param callbackInfo unused
     * @param packetIn     chat packet received
     **/
    @Inject(method = "handleChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER), cancellable = true)
    private void onChatPacket(S02PacketChat packetIn, CallbackInfo callbackInfo) {
        ChatReceivedEvent event;
        if (packetIn.getType() == 2) event = new ActionBarEvent(packetIn.getChatComponent());
        else event = new ChatReceivedEvent(packetIn.getChatComponent());
        event.post();
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
}
