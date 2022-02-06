package com.evieclient.mixins.client.renderer.entity;

import com.evieclient.utils.api.SocketClient;
import com.evieclient.utils.api.User;
import com.evieclient.utils.render.EvieGuiScreen;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


@Mixin(Render.class)
abstract class RenderMixin {

    private static ResourceLocation icon;

    private static boolean getIcon() {
        ClassLoader classLoader = EvieGuiScreen.class.getClassLoader();

        if (icon != null) return true;
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(classLoader.getResourceAsStream("assets/evie/textures/misc/icon.png"));
        } catch (Exception e) {
            Sentry.captureException(e);
            return false;
        }
        if (bufferedImage != null && icon == null) {
            icon = Minecraft.getMinecraft().getRenderManager().renderEngine.getDynamicTextureLocation("assets/evie/textures/misc/icon.png", new DynamicTexture(bufferedImage));
        } else {
            return false;
        }

        return true;
    }

    @Shadow
    public abstract FontRenderer getFontRendererFromRenderManager();

    @Inject(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;getWorldRenderer()Lnet/minecraft/client/renderer/WorldRenderer;", shift = At.Shift.AFTER), cancellable = true)
    private <T extends Entity> void renderLivingLabelPre(T entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo callbackInfo) {
        if (entityIn instanceof EntityWolf) {
            if (entityIn.getName().equals("Evie")) {
                if (icon != null) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(icon);
                    Gui.drawModalRectWithCustomSizedTexture(-getFontRendererFromRenderManager().getStringWidth(entityIn.getDisplayName().getFormattedText()) / 2 - 12, -2, 10, 10, 10, 10, 10, 10);
                } else {
                    getIcon();
                }
            }
        }


        if (entityIn instanceof AbstractClientPlayer) {

            if (!SocketClient.users.containsKey(entityIn.getName())) {
                SocketClient.users.put(entityIn.getName(), new User(entityIn.getName()));
            }
            if (SocketClient.users.get(entityIn.getName()).isEvieUser()) {
                if (icon != null) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(icon);
                    Gui.drawModalRectWithCustomSizedTexture(-getFontRendererFromRenderManager().getStringWidth(entityIn.getDisplayName().getFormattedText()) / 2 - 12, -2, 10, 10, 10, 10, 10, 10);
                } else {
                    getIcon();
                }
            }
        } else {

        }
    }
}
