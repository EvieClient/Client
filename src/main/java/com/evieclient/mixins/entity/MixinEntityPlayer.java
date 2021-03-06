package com.evieclient.mixins.entity;

import com.evieclient.events.impl.player.EventAttackEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin Event
 */
@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    /**
     * posts a {@link EventAttackEntity}.
     **/
    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("RETURN"))
    public void attackTargetEntityWithCurrentItem(Entity targetEntity, CallbackInfo ci) {
        if (targetEntity != null) {
            EventAttackEntity event = new EventAttackEntity(Minecraft.getMinecraft().thePlayer, targetEntity);
        }
    }
}
