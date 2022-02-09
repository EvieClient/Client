package com.evieclient.utils.api

import com.evieclient.utils.misc.ThreadManager
import com.evieclient.utils.render.LoadTexture
import com.google.gson.Gson
import io.sentry.Sentry
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderPlayer
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.entity.player.EnumPlayerModelParts
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import java.awt.image.BufferedImage
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean
import javax.imageio.ImageIO

class Capes {

    private val gson = Gson()
    private val sentReqForPlayer: HashMap<AbstractClientPlayer, AtomicBoolean> = HashMap<AbstractClientPlayer, AtomicBoolean>()
    private val capeTextures: HashMap<String, ResourceLocation> = HashMap<String, ResourceLocation>()

    fun downloadCape(cid: String) {
        ThreadManager.runAsync {
            val textureUrl = "https://evie.pw/api/getCapeTexture?id=$cid"
            val url = URL(textureUrl)

            val bufferedImage: BufferedImage
            bufferedImage = try {
                val inputStream = url.openStream()
                ImageIO.read(inputStream)
            } catch (e: Exception) {
                Sentry.captureException(e)
                return@runAsync
            }
            if (bufferedImage != null) {
                val resourceLocation = Minecraft.getMinecraft().renderManager.renderEngine.getDynamicTextureLocation(
                    url.toString(),
                    DynamicTexture(bufferedImage)
                )
                capeTextures[cid] = resourceLocation
            } else null
        }
    }




    fun tryRenderCape(playerRenderer: RenderPlayer, player: AbstractClientPlayer, partialTicks: Float): Boolean {
        if (
            !player.hasPlayerInfo()
            || player.isInvisible
            || !player.isWearing(EnumPlayerModelParts.CAPE)
        ) return false

        if (!EviePlayers.playerExists(player.uniqueID.toString())) {
            sentReqForPlayer[player] = AtomicBoolean(false)
            if (sentReqForPlayer[player]!!.compareAndSet(false, true)) {
                ThreadManager.runAsync(Runnable {
                    EvieRestAPI.reqEviePlayer(player.uniqueID.toString())
                })
            }
        }


        return if (EviePlayers.playerExists(player.uniqueID.toString()) && EviePlayers.getPlayer(player.uniqueID.toString())?.activeCosmetics?.cape != null) {
            if(!capeTextures.containsKey(EviePlayers.getPlayer(player.uniqueID.toString())?.activeCosmetics?.cape)) {
                downloadCape(EviePlayers.getPlayer(player.uniqueID.toString())?.activeCosmetics?.cape!!)
                false
            } else {
                renderCape(playerRenderer, player, partialTicks, capeTextures[EviePlayers.getPlayer(player.uniqueID.toString())?.activeCosmetics?.cape]!!)
            }
        } else {
            false
        }
    }


    private fun renderCape(
        playerRenderer: RenderPlayer,
        player: AbstractClientPlayer,
        partialTicks: Float,
        cape: ResourceLocation
    ): Boolean {
        renderCapeLayer(playerRenderer, player, cape, partialTicks)

        return true
    }

    private fun renderCapeLayer(
        renderer: RenderPlayer,
        entitylivingbaseIn: AbstractClientPlayer,
        CapeTexture: ResourceLocation,
        partialTicks: Float
    ) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        renderer.bindTexture(CapeTexture)
        GlStateManager.pushMatrix()
        GlStateManager.translate(0.0f, 0.0f, 0.125f)
        val d0: Double =
            entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks.toDouble() - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks.toDouble())
        val d1: Double =
            entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks.toDouble() - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks.toDouble())
        val d2: Double =
            entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks.toDouble() - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks.toDouble())
        val f: Float =
            entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks
        val d3 = MathHelper.sin(f * Math.PI.toFloat() / 180.0f).toDouble()
        val d4 = (-MathHelper.cos(f * Math.PI.toFloat() / 180.0f)).toDouble()
        var f1 = d1.toFloat() * 10.0f
        f1 = MathHelper.clamp_float(f1, -6.0f, 32.0f)
        var f2 = (d0 * d3 + d2 * d4).toFloat() * 100.0f
        val f3 = (d0 * d4 - d2 * d3).toFloat() * 100.0f

        if (f2 < 0.0f) {
            f2 = 0.0f
        }

        val f4: Float =
            entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks
        f1 =
            f1 + MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0f) * 32.0f * f4

        if (entitylivingbaseIn.isSneaking) {
            f1 += 25.0f
        }

        GlStateManager.rotate(6.0f + f2 / 2.0f + f1, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(f3 / 2.0f, 0.0f, 0.0f, 1.0f)
        GlStateManager.rotate(-f3 / 2.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f)
        renderer.mainModel.renderCape(0.0625f)
        GlStateManager.popMatrix()
    }
}