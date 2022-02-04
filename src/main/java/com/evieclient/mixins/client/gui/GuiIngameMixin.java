package com.evieclient.mixins.client.gui;

import com.evieclient.Evie;
import com.evieclient.utils.render.AnimationUtils;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class) public class GuiIngameMixin {

    @Inject(method = "renderGameOverlay", at = @At("HEAD"))
    private void renderEvieRenderModules(float partialTicks, CallbackInfo ci) {
        Evie.MODULE_MANAGER.renderHook();
        Evie.log("rendered it mixing")
    }
}
