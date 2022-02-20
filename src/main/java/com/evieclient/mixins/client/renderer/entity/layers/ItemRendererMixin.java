package com.evieclient.mixins.client.renderer.entity.layers;

import com.evieclient.Evie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.init.Items.filled_map;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private float prevEquippedProgress;
    @Shadow
    private float equippedProgress;
    @Shadow
    private ItemStack itemToRender;

    /**
     * @author twisttaan
     * Use mixins instead of casting.
     */
    @Shadow
    private void renderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress) {
    }

    @Shadow
    private void rotateArroundXAndY(float angle, float angleY) {
    }

    @Shadow
    private void setLightMapFromPlayer(AbstractClientPlayer clientPlayer) {
    }

    @Shadow
    private void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks) {
    }

    @Shadow
    private void doBlockTransformations() {
    }

    @Shadow
    private void doBowTransformations(float partialTicks, AbstractClientPlayer clientPlayer) {
    }

    @Shadow
    private void doItemUsedTransformations(float swingProgress) {
    }

    @Shadow
    private void renderPlayerArm(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress) {
    }

    @Shadow
    private void performDrinking(AbstractClientPlayer clientPlayer, float partialTicks) {
    }

    @Shadow
    public abstract void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform);

    /**
     * @author asbyth
     * @reason transform positioning
     */
    @Overwrite
    private void transformFirstPersonItem(float equipProgress, float swingProgress) {
        if (mc != null && mc.thePlayer != null && mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() != null
                && Item.getIdFromItem(mc.thePlayer.getItemInUse().getItem()) == 261) { // bow id
            if (Evie.MODULE_MANAGER.oldAnimations.getEnabled()) { // old bow position
                GlStateManager.translate(0.0f, 0.05f, 0.04f);
            }

            if (Evie.MODULE_MANAGER.oldAnimations.getEnabled()) { // old bow scale
                GlStateManager.scale(0.93f, 1.0f, 1.0f);
            }
        }

        if (mc != null && mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() != null
                && Item.getIdFromItem(mc.thePlayer.getCurrentEquippedItem().getItem()) == 346) { // rod id
            if (Evie.MODULE_MANAGER.oldAnimations.getEnabled()) { // old rod position
                GlStateManager.translate(0.08f, -0.027f, -0.33f);
            }

            if (Evie.MODULE_MANAGER.oldAnimations.getEnabled()) { // old rod scale
                GlStateManager.scale(0.93f, 1.0f, 1.0f);
            }
        }
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void attemptSwing() {
        if (mc.thePlayer.getItemInUseCount() > 0) {
            boolean mouseDown = mc.gameSettings.keyBindAttack.isKeyDown() && mc.gameSettings.keyBindUseItem.isKeyDown();
            if (mouseDown) {//&& mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                swingItem(mc.thePlayer);
            }
        }
    }

    private void swingItem(EntityPlayerSP entityplayersp) {
        final int swingAnimationEnd = entityplayersp.isPotionActive(Potion.digSpeed) ? (6 - (1 + entityplayersp.getActivePotionEffect(Potion.digSpeed).getAmplifier())) : (entityplayersp.isPotionActive(Potion.digSlowdown) ? (6 + (1 + entityplayersp.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
        if (!entityplayersp.isSwingInProgress || entityplayersp.swingProgressInt >= swingAnimationEnd / 2 || entityplayersp.swingProgressInt < 0) {
            entityplayersp.swingProgressInt = -1;
            entityplayersp.isSwingInProgress = true;
        }
    }

    /**
     * @author asbyth
     * @reason block hitting, swing while eating / aiming
     */
    @Overwrite
    public void renderItemInFirstPerson(float partialTicks) {
        try {
            float equipProgress = 1.0F - (prevEquippedProgress + (equippedProgress - prevEquippedProgress) * partialTicks);
            EntityPlayerSP player = mc.thePlayer;
            float swingProgress = player.getSwingProgress(partialTicks);
            float rotationPitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
            float rotationYaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;

            rotateArroundXAndY(rotationPitch, rotationYaw);
            setLightMapFromPlayer(player);
            rotateWithPlayerRotations(player, partialTicks);

            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            if (itemToRender != null) {
                attemptSwing();
                if (itemToRender.getItem() == filled_map) {
                    renderItemMap(player, rotationPitch, equipProgress, swingProgress);
                } else if (player.getItemInUseCount() > 0) {
                    EnumAction action = itemToRender.getItemUseAction();

                    if (action == EnumAction.DRINK) {
                        performDrinking(player, partialTicks);
                        if (Evie.MODULE_MANAGER.oldAnimations.getEnabled()) { // TODO: When a config system is implemented, this should be moved to the config
                            transformFirstPersonItem(equipProgress, swingProgress);
                        } else {
                            transformFirstPersonItem(equipProgress, 0.0F);
                        }
                    } else if (action == EnumAction.EAT) {
                        performDrinking(player, partialTicks);
                        if (Evie.MODULE_MANAGER.oldAnimations.getEnabled()) { // TODO: When a config system is implemented, this should be moved to the config
                            transformFirstPersonItem(equipProgress, swingProgress);
                        } else {
                            transformFirstPersonItem(equipProgress, 0.0F);
                        }
                    } else if (action == EnumAction.BLOCK) {
                        if (Evie.MODULE_MANAGER.oldAnimations.getEnabled()) { // TODO: When a config system is implemented, this should be moved to the config
                            transformFirstPersonItem(equipProgress, swingProgress);
                            doBlockTransformations();
                            GlStateManager.scale(0.83f, 0.88f, 0.85f);
                            GlStateManager.translate(-0.3f, 0.1f, 0.0f);
                        } else {
                            transformFirstPersonItem(equipProgress, 0.0F);
                            doBlockTransformations();
                        }
                    } else if (action == EnumAction.BOW) {
                        transformFirstPersonItem(equipProgress, 0.0F);
                        doBowTransformations(partialTicks, player);
                    }

                } else {
                    doItemUsedTransformations(swingProgress);
                    transformFirstPersonItem(equipProgress, swingProgress);
                }
                renderItem(player, itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
            } else if (!player.isInvisible()) {
                renderPlayerArm(player, equipProgress, swingProgress);
            }
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

