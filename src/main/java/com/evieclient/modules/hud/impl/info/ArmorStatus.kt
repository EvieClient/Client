package com.evieclient.modules.hud.impl.info

import com.evieclient.modules.Category
import com.evieclient.modules.hud.RenderModule
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.item.ItemStack
import org.lwjgl.opengl.GL11


class ArmorStatus :
    RenderModule(
        "Armor Status",
        "Shows your armor status",
        Category.INFO,
        true
    ) {
    private val hor: Boolean = false

    override fun getWidth(): Int {
        return if (hor) {
            78
        } else {
            17
        }
    }

    override fun getHeight(): Int {
        return if (hor) {
            20
        } else {
            63
        }
    }

    override fun renderModule() {
        if (hor) {
            mc.renderItem.renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(3), x, y + 2)
            mc.renderItem.renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(2), x + 20, y + 2)
            mc.renderItem.renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(1), x + 40, y + 2)
            mc.renderItem.renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(0), x + 60, y + 2)
        } else {
            for (i in mc.thePlayer.inventory.armorInventory.indices) {
                val itemStack = mc.thePlayer.inventory.armorInventory[i]
                renderItemStack(x, y, i, itemStack)
            }
        }
    }

    private fun renderItemStack(x: Int, y: Int, i: Int, `is`: ItemStack?) {
        if (`is` == null) {
            return
        }
        GL11.glPushMatrix()
        val yAdd = -16 * i + 48

        RenderHelper.enableGUIStandardItemLighting()
        mc.renderItem.renderItemAndEffectIntoGUI(`is`, x, y + yAdd)
        // render durability
        if (`is`.isItemStackDamageable) {
            val health = `is`.itemDamage
            val maxHealth = `is`.maxDamage
            val healthPercent = health.toFloat() / maxHealth.toFloat()
            val healthWidth = (healthPercent * 16).toInt()
            val healthHeight = 3
            val healthX = x + 16 - healthWidth
            val healthY = y + yAdd + 16 - healthHeight
            fr.drawStringWithShadow(
                "$healthPercent%",
                healthX.toFloat(),
                healthY.toFloat(),
                damageColor(healthPercent)
            )
        }
        RenderHelper.disableStandardItemLighting()
        GL11.glPopMatrix()
    }

    private fun damageColor(health: Float): Int {
        return when {
            health < 0.5f -> 0xFF0000
            health < 0.75f -> 0xFFFF00
            else -> 0xCA653A
        }
    }
}