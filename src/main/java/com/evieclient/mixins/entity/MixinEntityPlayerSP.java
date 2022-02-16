package com.evieclient.mixins.entity;

import com.evieclient.events.impl.client.PlayerChatEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.world.World;
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
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

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
