package com.evieclient.mixins.client.renderer.entity.layers;

import com.evieclient.Evie;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerCape.class)
abstract class CapeLayerMixin implements LayerRenderer<AbstractClientPlayer> {
    @Shadow
    @Final
    private RenderPlayer playerRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V", at = @At("HEAD"), cancellable = true)
    void doRenderLayer(
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount ,
            float partialTicks,
            float ageInTicks ,
            float netHeadYaw ,
            float headPitch ,
            float scale ,
            CallbackInfo ci
    ) {
        if (Evie.ECAPES.tryRenderCape(playerRenderer, player, partialTicks)) ci.cancel();
    }
}