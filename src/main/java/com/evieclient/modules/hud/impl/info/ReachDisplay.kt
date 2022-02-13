package com.evieclient.modules.hud.impl.info

import com.evieclient.modules.Category
import com.evieclient.modules.hud.RenderModule
import net.minecraft.entity.EntityLivingBase

class ReachDisplay : RenderModule("ReachDisplay", "View the reach from your last hit.", Category.INFO, true) {
    var hit: EntityLivingBase? = null
    var reach = 0.0
    override fun renderModule() {
        hit = mc.pointedEntity as EntityLivingBase
        if (hit != null) {
            if (hit!!.hurtTime > 0) {
                reach = (mc.thePlayer.getDistanceToEntity(hit) - 1).toDouble()
            }
        }
        val n = 3
        fr.drawStringWithShadow(("" + reach).substring(0, n).toDouble().toString() + "", x.toFloat(), y.toFloat(), -1)
    }

    override fun getWidth(): Int {
        return fr.getStringWidth(("" + reach).substring(0, 3).toDouble().toString() + "")
    }

    override fun getHeight(): Int {
        return fr.FONT_HEIGHT
    }
}