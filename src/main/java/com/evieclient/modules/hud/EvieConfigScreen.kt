package com.evieclient.modules.hud

import com.evieclient.Evie
import com.evieclient.modules.Module
import com.evieclient.utils.render.FontRenderer
import com.evieclient.utils.saving.Save
import com.evieclient.utils.ui.Checkbox
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Mouse
import java.awt.Color
import java.util.ArrayList

class EvieConfigScreen : GuiScreen() {

    // FontRenderers
    var smfr = FontRenderer("/fonts/Roboto-Thin.ttf", 15f)
    var mdfr = FontRenderer("/fonts/Roboto-Regular.ttf", 20f)
    var lgfr = FontRenderer("/fonts/Roboto-Medium.ttf", 25f)

    // Variables & Sizes
    val modules: ArrayList<Module> = Evie.MODULE_MANAGER.modules
    var scrollPercent = 0f

    override fun handleMouseInput() {
        // handle mouse scrolling
        val dWheel = Mouse.getDWheel()
        if (dWheel != 0) {
            scrollPercent += dWheel / 120f
            if (scrollPercent < 0) scrollPercent = 0f
            if (scrollPercent > 1) scrollPercent = 1f
        }
        super.handleMouseInput()
    }


    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
//        val boxLeft = width / 2 - 250
//        val boxTop = height / 2 - 150
//        val boxRight = width / 2 + 250
//        val boxBottom = height / 2 + 150
//
//        // draw a rectangle wider than taller in the middle of the screen to hold all the modules
//        drawRect(boxLeft, boxTop, boxRight, boxBottom, Color(0, 0, 0, 100).rgb)

        // Make a big box to hold all the modules which are scrollable smaller rectangles with the module name and description
        val boxLeft = width / 2 - 250
        val boxTop = height / 2 - 150
        val boxRight = width / 2 + 250
        val boxBottom = height / 2 + 150

        // draw a rectangle wider than taller in the middle of the screen to hold all the modules
        drawRect(boxLeft, boxTop, boxRight, boxBottom, Color(0, 0, 0, 100).rgb)

        // draw the modules
        modules.forEach {
           // now that we made a big box render some smaller boxes for the modules make sure to use the scrollPercent so we can scroll through the modules and modules.indexOf(it) so we know how to order our modules
            // if the text is too long to fit in the box then cut it down to fit, but if we hover over the module then show the full text

            // Sketch of design:
            // _________________________________________________
            // |           |           |           |           |
            // |    title  |   title   |   title   |   title   |
            // |    desc   |  desc     |   desc    |   desc    |
            // _________________________________________________
            // |           |           |           |           |
            // |    title  |   title   |   title   |   title   |
            // |    desc   |  desc     |   desc    |   desc    |
            // _________________________________________________

            // Variables Respecting the box, current scroll and index of the module
            val boxWidth = boxRight - boxLeft
            val boxHeight = boxBottom - boxTop
            val moduleWidth = boxWidth / 4
            val moduleHeight = boxHeight / 4
            val moduleLeft = boxLeft + (moduleWidth * (modules.indexOf(it) % 4))
            val moduleTop = boxTop + (moduleHeight * (modules.indexOf(it) / 4)) // + (scrollPercent * boxHeight)
            val moduleRight = moduleLeft + moduleWidth
            val moduleBottom = moduleTop + moduleHeight
            val moduleTextLeft = moduleLeft + 5
            val moduleTextTop = moduleTop + 5
            val moduleTextRight = moduleRight - 5
            val moduleTextBottom = moduleBottom - 5
            val moduleTextWidth = moduleTextRight - moduleTextLeft
            val moduleTextHeight = moduleTextBottom - moduleTextTop

            // Draw the module box
            drawRect(moduleLeft, moduleTop.toInt(), moduleRight, moduleBottom.toInt(), Color(0, 0, 0, 100).rgb)

            // Cut off text if it's too long to fit in the box and draw the text but while hovering over the module show the full text
            val Name = if (moduleTextWidth < mdfr.getWidth(it.name)) {
                if (mouseX in moduleLeft..moduleRight && mouseY in moduleTop..moduleBottom) {
                    it.name
                } else {
                    it.name.substring(0, it.name.length - 3) + "..."
                }
            } else {
                it.name
            }

            val Description = if (moduleTextWidth < mdfr.getWidth(it.description)) {
                if (mouseX in moduleLeft..moduleRight && mouseY in moduleTop..moduleBottom) {
                    it.description
                } else {
                    it.description.substring(0, it.description.length - 3) + "..."
                }
            } else {
                it.description
            }

            // Draw the module Title
            mdfr.drawString(Name, moduleTextLeft.toFloat(), moduleTextTop.toFloat(), Color(255, 255, 255))

            // Draw the module Description
            mdfr.drawString(Description, moduleTextLeft.toFloat(), moduleTextTop.toFloat() + mdfr.getHeight(it.name), Color(255, 255, 255))

            // make a checkbox() for the module
           // Checkbox(Evie.MODULE_MANAGER.modules.indexOf(it), moduleLeft, moduleTop,  it.enabled)



        }




        Evie.evieLogo.drawPinkBars(this.width, this.height)
        // drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun initGui() {
        buttonList.clear()

        super.initGui()
        // draw an x button to close the gui, put it at the top right corner of the box
        buttonList.add(GuiButton(0, width / 2 + 150 - 20, height / 2 - 150 - 20 + 20, 20, 20, "X"))

        // for every module, make a checkbox that will save the state of the module
//        Evie.MODULE_MANAGER.modules.forEach {
//            this.buttonList.add(
//                Checkbox(
//                    Evie.MODULE_MANAGER.modules.indexOf(it),
//                    this.width / 2 - 150 + 10 + mdfr.getWidth(it.name) + 5,
//                    this.height / 2 - 150 + 10 + (Evie.MODULE_MANAGER.modules.indexOf(it) * 30),
//                    it.enabled
//                )
//            )
//        }
    }

    override fun actionPerformed(button: GuiButton?) {
        super.actionPerformed(button);
        if (button != null) {
            if(button.id == 0) {
                Minecraft.getMinecraft().displayGuiScreen(HUDConfigScreen())
            }
            if(button is Checkbox) {
                Evie.MODULE_MANAGER.modules[button.id].enabled = button.isChecked
                Save.saveConfig()
            }
        }
    }

    override fun onGuiClosed() {
        Save.saveConfig()
        super.onGuiClosed()
    }
}