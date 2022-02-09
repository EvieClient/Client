package com.evieclient.utils.api

import com.evieclient.Evie
import com.evieclient.utils.render.LoadTexture
import com.google.gson.Gson
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderPlayer
import net.minecraft.entity.player.EnumPlayerModelParts
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation

class Capes {

    private val gson = Gson()

    fun tryRenderCape(playerRenderer: RenderPlayer, player: AbstractClientPlayer, partialTicks: Float): Boolean {
        if (
                !player.hasPlayerInfo()
                || player.isInvisible()
                || !player.isWearing(EnumPlayerModelParts.CAPE)
                ) return false

        return if (true) {
            Evie.log("Trying to render cape for ${player.name}")
            renderCape(playerRenderer, player, partialTicks, "SimpleEvieCape")
        } else {
            false
        }
    }


    private fun renderCape(playerRenderer: RenderPlayer, player: AbstractClientPlayer, partialTicks: Float, cape: String): Boolean {
        renderCapeLayer(playerRenderer, player, CapeTexture.PRIMARY, partialTicks, cape)

        return true
    }

    private fun renderCapeLayer(renderer: RenderPlayer, entitylivingbaseIn: AbstractClientPlayer, texture: CapeTexture, partialTicks: Float, cid: String) {
        Evie.log("Rendering cape layer for ${entitylivingbaseIn.name}")


        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        renderer.bindTexture(texture.location)
        GlStateManager.pushMatrix()
        GlStateManager.translate(0.0f, 0.0f, 0.125f)
        val d0: Double = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks.toDouble() - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks.toDouble())
        val d1: Double = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks.toDouble() - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks.toDouble())
        val d2: Double = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks.toDouble() - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks.toDouble())
        val f: Float = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks
        val d3 = MathHelper.sin(f * Math.PI.toFloat() / 180.0f).toDouble()
        val d4 = (-MathHelper.cos(f * Math.PI.toFloat() / 180.0f)).toDouble()
        var f1 = d1.toFloat() * 10.0f
        f1 = MathHelper.clamp_float(f1, -6.0f, 32.0f)
        var f2 = (d0 * d3 + d2 * d4).toFloat() * 100.0f
        val f3 = (d0 * d4 - d2 * d3).toFloat() * 100.0f

        if (f2 < 0.0f) {
            f2 = 0.0f
        }

        val f4: Float = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks
        f1 = f1 + MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0f) * 32.0f * f4

        if (entitylivingbaseIn.isSneaking()) {
            f1 += 25.0f
        }

        GlStateManager.rotate(6.0f + f2 / 2.0f + f1, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(f3 / 2.0f, 0.0f, 0.0f, 1.0f)
        GlStateManager.rotate(-f3 / 2.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f)
        renderer.mainModel.renderCape(0.0625f)
        GlStateManager.popMatrix()
    }


    private enum class CapeTexture(val location: ResourceLocation) {
        PRIMARY(LoadTexture.LoadTexture("assets/evie/textures/misc/EO9KF.png")),
    }

}