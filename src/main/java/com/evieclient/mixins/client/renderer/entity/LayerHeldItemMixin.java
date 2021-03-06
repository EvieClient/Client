package com.evieclient.mixins.client.renderer.entity;

import com.evieclient.handlers.OldAnimationsHandler;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerHeldItem.class)
public abstract class LayerHeldItemMixin implements LayerRenderer<EntityLivingBase> {

    @Shadow @Final private RendererLivingEntity<?> livingEntityRenderer;

    private final OldAnimationsHandler oldAnimationsHandler = new OldAnimationsHandler();

    /**
     * @author twisttaan
     */
    @Overwrite
    public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        oldAnimationsHandler.doRenderLayer(entity, livingEntityRenderer);
    }
}
