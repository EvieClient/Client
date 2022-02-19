package com.evieclient.mixins.client.gui;

import com.evieclient.Evie;
import com.evieclient.events.impl.client.RenderEvent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
abstract public class GuiIngameMixin extends Gui {

    @Inject(method = "renderGameOverlay", at = @At("TAIL"))
    private void renderEvieRenderModules(float partialTicks, CallbackInfo ci) {
        Evie.MODULE_MANAGER.renderHook();
        new RenderEvent().post();
    }
}
