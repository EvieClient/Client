package com.evieclient.utils.ui

import com.evieclient.utils.render.EvieGuiScreen
import io.sentry.Sentry
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.util.ResourceLocation
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class EvieLogo {
    private var evieIcon: ResourceLocation? = null

    init {
        if (evieIcon == null) {
            val success = getECITexture()
            if (!success
            ) {
                Sentry.captureMessage("Failed to load EvieClient logo texture")
            }
        }

    }

    fun draw(
        x: Int,
        y: Int,
        width: Int
    ) {
        drawTexture(
            x,
            y,
            width,
            width,
            evieIcon!!
        )
    }

    private fun drawTexture(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        texture: ResourceLocation
    ) {
        Minecraft.getMinecraft().textureManager.bindTexture(texture)
        Gui.drawModalRectWithCustomSizedTexture(
            x,
            y,
            0f,
            0f,
            width,
            height,
            width.toFloat(),
            height.toFloat()
        )
    }


    private fun getECITexture(): Boolean {
        val classLoader = EvieGuiScreen::class.java.classLoader
        if (evieIcon != null) return true
        val bufferedImage: BufferedImage
        bufferedImage = try {
            ImageIO.read(classLoader.getResourceAsStream("assets/evie/textures/misc/icon.png"))
        } catch (e: Exception) {
            Sentry.captureException(e)
            return false
        }
        if (bufferedImage != null && evieIcon == null) {
            evieIcon =
                Minecraft.getMinecraft().renderManager.renderEngine.getDynamicTextureLocation(
                    "assets/evie/textures/misc/icon.png",
                    DynamicTexture(bufferedImage)
                )
        } else {
            return false
        }
        return true
    }
}