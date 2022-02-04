package com.evieclient.modules.hud.impl.info;

import com.evieclient.Evie;
import com.evieclient.modules.Category;
import com.evieclient.modules.hud.RenderModule;
import net.minecraft.entity.EntityLivingBase;

public class ReachDisplay extends RenderModule {

    EntityLivingBase hit = null;
    double reach;

    public ReachDisplay() {
        super("ReachDisplay", "Displays your reach", Category.INFO, true);
    }

    @Override
    public void render() {
        hit = (EntityLivingBase) mc.pointedEntity;
        if (hit != null) {
            if (hit.hurtTime > 0) {
                reach = mc.thePlayer.getDistanceToEntity(hit) - 1;
            }
        }
        int n = 3;

        fr.drawStringWithShadow(Double.parseDouble(("" + reach).substring(0, n)) + "", getX(), getY(), -1);

    }

    @Override
    public int getWidth() {
        return fr.getStringWidth("3.00");
    }


    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }

}
