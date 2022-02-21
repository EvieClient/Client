package com.evieclient.utils.ui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.renderer.GlStateManager
import java.awt.Color

class Checkbox @JvmOverloads constructor(buttonId: Int, x: Int, y: Int, var isChecked: Boolean = false) :
    GuiButton(buttonId, x.toInt(), y.toInt(), 20, 20, "") {

    override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int) {
        if (visible) {
            val fontrenderer = mc.fontRendererObj
            mc.textureManager.bindTexture(buttonTextures)
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
            hovered =
                mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height
            val i = getHoverState(hovered)
            GlStateManager.enableBlend()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            GlStateManager.blendFunc(770, 771)
            this.drawTexturedModalRect(xPosition, yPosition, 0, 46 + i * 20, width / 2, height)
            this.drawTexturedModalRect(
                xPosition + width / 2,
                yPosition, 200 - width / 2, 46 + i * 20, width / 2, height
            )
            mouseDragged(mc, mouseX, mouseY)
            displayString = X
            var color = X_COLOR.rgb
            if (isChecked) {
                displayString = CHECK
                color = CHECK_COLOR.rgb
            }
            drawCenteredString(
                fontrenderer,
                displayString, xPosition + width / 2, yPosition + (height - 8) / 2, color
            )
        }
    }

    override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            isChecked = !isChecked
            return true
        }
        return false
    }

    companion object {
        private const val X = "✗"
        private const val CHECK = "✔"
        private val X_COLOR = Color.RED
        private val CHECK_COLOR = Color.GREEN
    }
}