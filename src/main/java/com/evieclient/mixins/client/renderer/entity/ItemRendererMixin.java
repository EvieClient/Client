package com.evieclient.mixins.client.renderer.entity;

import com.evieclient.Evie;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ItemRenderer.class)
public class    ItemRendererMixin {
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private float equippedProgress;
    private float swingProgress;

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItemUseAction()Lnet/minecraft/item/EnumAction;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void modifySwingProgress(float partialTicks, CallbackInfo ci, float f, AbstractClientPlayer player, float f1, float f2, float f3) {
        this.swingProgress = f1;
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", shift = At.Shift.AFTER))
    private void modifySwing(float partialTicks, CallbackInfo ci) {
        if (Evie.MODULE_MANAGER.oldAnimations.isEnabled()) {
            GlStateManager.scale(0.83f, 0.88f, 0.85f);
            GlStateManager.translate(-0.3f, 0.1f, 0.0f);
        }
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void modifyEat(float partialTicks, CallbackInfo ci) {
        if (Evie.MODULE_MANAGER.oldAnimations.isEnabled()) {
            GlStateManager.scale(0.8f, 1.0f, 1.0f);
            GlStateManager.translate(-0.2f, -0.1f, 0.0f);
        }
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2, shift = At.Shift.AFTER))
    private void modifyBlock(float partialTicks, CallbackInfo ci) {
        if (Evie.MODULE_MANAGER.oldAnimations.isEnabled()) {
            GlStateManager.translate(-0.5f, -0.2f, 0.0f);
        }
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2), index = 0)
    private float blockSwingEquip(float equippedProgress, float f1) {
        return Evie.MODULE_MANAGER.oldAnimations.isEnabled() ? f1 : equippedProgress;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2), index = 1)
    private float blockSwingProgress(float swingProgress) {
        return Evie.MODULE_MANAGER.oldAnimations.isEnabled() ? this.swingProgress : swingProgress;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1), index = 1)
    private float drinkSwingProgress(float swingProgress) {
        return Evie.MODULE_MANAGER.oldAnimations.isEnabled() ? this.swingProgress : 0.0f;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 3), index = 1)
    private float bowSwingProgress(float swingProgress) {
        return Evie.MODULE_MANAGER.oldAnimations.isEnabled() ? this.swingProgress : 0.0f;
    }
}