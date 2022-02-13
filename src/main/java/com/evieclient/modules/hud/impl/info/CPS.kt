package com.evieclient.modules.hud.impl.info

import com.evieclient.modules.Category
import com.evieclient.modules.hud.RenderModule
import net.minecraft.entity.EntityLivingBase
import org.lwjgl.input.Mouse
import java.util.ArrayList

class CPS : RenderModule("CPS", "View your CPS", Category.INFO, true) {
    private val clicks: MutableList<Long> = ArrayList()
    private var wasPressed = false
    private var lastPressed: Long = 0

    override fun renderModule() {
        val lpressed = Mouse.isButtonDown(0)
        if (lpressed != wasPressed) {
            lastPressed = System.currentTimeMillis() + 10
            wasPressed = lpressed
            if (lpressed) {
                clicks.add(lastPressed)
            }
        }
        fr.drawStringWithShadow("$cPS", x.toFloat(), y.toFloat(), -1)
    }

    private val cPS: Int
        get() {
            val time = System.currentTimeMillis()
            clicks.removeIf { aLong: Long -> aLong + 1000 < time }
            return clicks.size
        }

    override fun getWidth(): Int {
        return fr.getStringWidth("123")
    }

    override fun getHeight(): Int {
        return fr.FONT_HEIGHT
    }
}