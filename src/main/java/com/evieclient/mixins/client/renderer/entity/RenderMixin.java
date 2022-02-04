package com.evieclient.mixins.client.renderer.entity;

import com.evieclient.Evie;
import com.evieclient.utils.api.SocketClient;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import static com.evieclient.Evie.evieDir;


@Mixin(Render.class)
abstract class RenderMixin {
    @Shadow public abstract FontRenderer getFontRendererFromRenderManager();

    private static ResourceLocation iconTexture;
    private static final File icon = new File(evieDir, "assets/textures/gui/icon.png");

    @Inject(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;getWorldRenderer()Lnet/minecraft/client/renderer/WorldRenderer;", shift = At.Shift.AFTER),  cancellable = true)
    private <T extends Entity> void renderLivingLabelPre(T entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo callbackInfo) {
        if(entityIn instanceof AbstractClientPlayer) {
            boolean success = getIcon();
            if(success) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(iconTexture);
                Gui.drawModalRectWithCustomSizedTexture(-getFontRendererFromRenderManager().getStringWidth(entityIn.getDisplayName().getFormattedText()) / 2 - 12, -2, 10, 10, 10, 10, 10, 10);
            }

            } else {

            }
        }

    private static boolean getIcon() {
        if (iconTexture != null) return true;
        if (icon.exists()) {
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(new FileInputStream(icon));
            } catch (Exception e) {
                Sentry.captureException(e);
                return false;
            }
            if (bufferedImage != null && iconTexture == null) {
                iconTexture = Minecraft.getMinecraft().getRenderManager().renderEngine.getDynamicTextureLocation(icon.getName(), new DynamicTexture(bufferedImage));
            }
        } else {
            return false;
        }

        return true;
    }
}
