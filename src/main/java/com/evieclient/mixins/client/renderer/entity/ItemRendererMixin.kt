package com.evieclient.mixins.client.renderer.entity

import org.spongepowered.asm.mixin.Mixin
import net.minecraft.client.renderer.ItemRenderer
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.Final
import net.minecraft.client.Minecraft
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import net.minecraft.client.entity.AbstractClientPlayer
import com.evieclient.Evie
import net.minecraft.client.renderer.GlStateManager
import org.spongepowered.asm.mixin.injection.ModifyArg

@Mixin(ItemRenderer::class)
class ItemRendererMixin {
    @Shadow
    @Final
    private val mc: Minecraft? = null

    @Shadow
    private val equippedProgress = 0f
    private var swingProgress = 0f
    @Inject(
        method = ["renderItemInFirstPerson"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;getItemUseAction()Lnet/minecraft/item/EnumAction;",
            shift = At.Shift.AFTER
        )],
        locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private fun modifySwingProgress(
        partialTicks: Float,
        ci: CallbackInfo,
        f: Float,
        player: AbstractClientPlayer,
        f1: Float,
        f2: Float,
        f3: Float
    ) {
        swingProgress = f1
    }

    @Inject(
        method = ["renderItemInFirstPerson"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
            shift = At.Shift.AFTER
        )]
    )
    private fun modifySwing(partialTicks: Float, ci: CallbackInfo) {
        if (Evie.MODULE_MANAGER.oldAnimations.isEnabled) {
            GlStateManager.scale(0.83f, 0.88f, 0.85f)
            GlStateManager.translate(-0.3f, 0.1f, 0.0f)
        }
    }

    @Inject(
        method = ["renderItemInFirstPerson"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
            ordinal = 1,
            shift = At.Shift.AFTER
        )]
    )
    private fun modifyEat(partialTicks: Float, ci: CallbackInfo) {
        if (Evie.MODULE_MANAGER.oldAnimations.isEnabled) {
            GlStateManager.scale(0.8f, 1.0f, 1.0f)
            GlStateManager.translate(-0.2f, -0.1f, 0.0f)
        }
    }

    @Inject(
        method = ["renderItemInFirstPerson"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
            ordinal = 2,
            shift = At.Shift.AFTER
        )]
    )
    private fun modifyBlock(partialTicks: Float, ci: CallbackInfo) {
        if (Evie.MODULE_MANAGER.oldAnimations.isEnabled) {
            GlStateManager.translate(-0.5f, -0.2f, 0.0f)
        }
    }

    @ModifyArg(
        method = ["renderItemInFirstPerson"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
            ordinal = 2
        ),
        index = 0
    )
    private fun blockSwingEquip(equippedProgress: Float, f1: Float): Float {
        return if (Evie.MODULE_MANAGER.oldAnimations.isEnabled) f1 else equippedProgress
    }

    @ModifyArg(
        method = ["renderItemInFirstPerson"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
            ordinal = 2
        ),
        index = 1
    )
    private fun blockSwingProgress(swingProgress: Float): Float {
        return if (Evie.MODULE_MANAGER.oldAnimations.isEnabled) this.swingProgress else swingProgress
    }

    @ModifyArg(
        method = ["renderItemInFirstPerson"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
            ordinal = 1
        ),
        index = 1
    )
    private fun drinkSwingProgress(swingProgress: Float): Float {
        return if (Evie.MODULE_MANAGER.oldAnimations.isEnabled) this.swingProgress else 0.0f
    }

    @ModifyArg(
        method = ["renderItemInFirstPerson"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
            ordinal = 3
        ),
        index = 1
    )
    private fun bowSwingProgress(swingProgress: Float): Float {
        return if (Evie.MODULE_MANAGER.oldAnimations.isEnabled) this.swingProgress else 0.0f
    }
}