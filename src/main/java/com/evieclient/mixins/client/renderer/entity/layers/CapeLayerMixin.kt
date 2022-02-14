package com.evieclient.mixins.client.renderer.entity.layers

import org.spongepowered.asm.mixin.Mixin
import net.minecraft.client.renderer.entity.layers.LayerCape
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.Final
import net.minecraft.client.renderer.entity.RenderPlayer
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.At
import net.minecraft.client.entity.AbstractClientPlayer
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import com.evieclient.Evie

@Mixin(LayerCape::class)
class CapeLayerMixin {
    @Shadow
    @Final
    private val playerRenderer: RenderPlayer? = null
    @Inject(method = ["doRenderLayer"], at = [At("HEAD")], cancellable = true)
    fun doRenderLayer(
        player: AbstractClientPlayer?,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float,
        scale: Float,
        ci: CallbackInfo
    ) {
        if (Evie.ECAPES.tryRenderCape(playerRenderer!!, player!!, partialTicks)) ci.cancel()
    }
}