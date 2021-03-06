package com.evieclient.utils.api

import io.sentry.Sentry
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.util.ResourceLocation
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

class TexturedCape(id: String) {

    private var texture: ResourceLocation? = getCapeTexture(id)

    fun getResourceLocation(): ResourceLocation? {
        return texture
    }

    private fun getCapeTexture(id: String): ResourceLocation? {
        val textureUrl = "http://172.105.171.202:3000/api/getCapeTexture?id=$id"
        val url = URL(textureUrl)
        val bufferedImage: BufferedImage
        bufferedImage = try {
            val inputStream = url.openStream()
            ImageIO.read(inputStream)
        } catch (e: Exception) {
            Sentry.captureException(e)
            return null
        }
        return if (bufferedImage != null && this.texture == null) {
            Minecraft.getMinecraft().renderManager.renderEngine.getDynamicTextureLocation(
                "assets/evie/textures/gui/evie_gradient.png",
                DynamicTexture(bufferedImage)
            )
        } else {
            null
        }
    }
}

// More Efficient way but not working


//package com.evieclient.utils.api
//
//import io.sentry.Sentry
//import net.minecraft.client.Minecraft
//import net.minecraft.client.renderer.texture.DynamicTexture
//import net.minecraft.util.ResourceLocation
//import java.awt.image.BufferedImage
//import java.net.URL
//import javax.imageio.ImageIO
//import kotlin.concurrent.thread
//
//class TexturedCape(id: String) {
//
//    init {
//        thread(start = true) {
//            getCapeTexture(id)
//        }
//    }
//
//    private var texture: ResourceLocation? = null
//
//    fun getResourceLocation(): ResourceLocation? {
//        return texture
//    }
//
//    private fun getCapeTexture(id: String) {
//        val textureUrl = "http://172.105.171.202:3000/api/getCapeTexture?id=$id"
//        val url = URL(textureUrl)
//        val bufferedImage: BufferedImage? = try {
//            val inputStream = url.openStream()
//            ImageIO.read(inputStream)
//        } catch (e: Exception) {
//            Sentry.captureException(e)
//            null
//        }
//        texture = if (bufferedImage != null && this.texture == null) {
//            Minecraft.getMinecraft().renderManager.renderEngine.getDynamicTextureLocation(
//                "assets/evie/textures/gui/evie_gradient.png",
//                DynamicTexture(bufferedImage)
//            )
//        } else {
//            null
//        }
//    }
//}
//
//


