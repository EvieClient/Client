package com.evieclient.utils.render;

import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class LoadTexture {

    public static ResourceLocation LoadTexture(String path) {
        BufferedImage bufferedImage;
        try {
            ClassLoader classLoader = LoadTexture.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(path);
            bufferedImage = ImageIO.read(inputStream);
        } catch (Exception e) {
            Sentry.captureException(e);
            return null;
        }
        if (bufferedImage != null) {
            return Minecraft.getMinecraft().getRenderManager().renderEngine.getDynamicTextureLocation(path, new DynamicTexture(bufferedImage));
        }
        return null;
    }
}
