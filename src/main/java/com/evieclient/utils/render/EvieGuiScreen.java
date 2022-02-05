package com.evieclient.utils.render;

import com.evieclient.utils.ResolutionUtil;
import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class EvieGuiScreen extends GuiScreen {
    private static ResourceLocation dynamicBackgroundTexture;

    public static void renderBackgroundImage() {
        boolean success = getCustomBackground();

        if (success) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(dynamicBackgroundTexture);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0,
                    ResolutionUtil.current().getScaledWidth(),
                    ResolutionUtil.current().getScaledHeight(),
                    ResolutionUtil.current().getScaledWidth(),
                    ResolutionUtil.current().getScaledHeight());
            return;
        }
    }

    private static boolean getCustomBackground() {
        ClassLoader classLoader = EvieGuiScreen.class.getClassLoader();

        if (dynamicBackgroundTexture != null) return true;
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(classLoader.getResourceAsStream("assets/evie/textures/gui/evie_gradient.png"));
        } catch (Exception e) {
            Sentry.captureException(e);
            return false;
        }
        if (bufferedImage != null && dynamicBackgroundTexture == null) {
            dynamicBackgroundTexture = Minecraft.getMinecraft().getRenderManager().renderEngine.getDynamicTextureLocation("assets/evie/textures/gui/evie_gradient.png", new DynamicTexture(bufferedImage));
        } else {
            return false;
        }

        return true;
    }
}

