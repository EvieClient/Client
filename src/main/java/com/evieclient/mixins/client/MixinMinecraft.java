package com.evieclient.mixins.client;

import com.evieclient.Evie;
import com.evieclient.events.impl.client.GameLoopEvent;
import com.evieclient.events.impl.client.input.KeyPressedEvent;
import com.evieclient.events.impl.client.input.KeyReleasedEvent;
import com.evieclient.events.impl.client.input.LeftClickEvent;
import com.evieclient.events.impl.client.input.RightClickEvent;
import com.evieclient.events.impl.world.LoadWorldEvent;
import com.evieclient.events.impl.world.SinglePlayerJoinEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.WorldSettings;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinBootstrap Events for Minecraft.class.
 *
 * @since 1.0.0
 **/
@Mixin(Minecraft.class)
public class MixinMinecraft {

    /**
     * Post {@link Evie} start.
     *
     * @param callbackInfo unused
     **/
    @Inject(method = "startGame", at = @At("RETURN"))
    private void onGameStart(CallbackInfo callbackInfo) {
        Evie.INSTANCE.postInitialisation();
    }

    /**
     * Post {@link Evie} shutdown.
     *
     * @param callbackInfo unused
     **/
    @Inject(method = "shutdown", at = @At("HEAD"))
    private void shutdown(CallbackInfo callbackInfo) {
        Evie.INSTANCE.shutdown();
    }

    /**
     * Post {@link GameLoopEvent} every tick.
     *
     * @param callbackInfo unused
     */
    @Inject(method = "runGameLoop", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;skipRenderWorld:Z", shift = At.Shift.AFTER))
    private void runGameLoop(CallbackInfo callbackInfo) {
        new GameLoopEvent().post();
    }

    /**
     * Post {@link KeyPressedEvent} or {@link KeyReleasedEvent} at key press.
     *
     * @param callbackInfo unused
     **/
    @Inject(method = "dispatchKeypresses", at = @At(value = "INVOKE_ASSIGN", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", remap = false))
    private void runTickKeyboard(CallbackInfo callbackInfo) {
        Evie.EVENT_BUS.post(Keyboard.getEventKeyState() ?
                new KeyPressedEvent(Keyboard.isRepeatEvent(), Keyboard.getEventKey())
                : new KeyReleasedEvent(Keyboard.isRepeatEvent(), Keyboard.getEventKey()));
    }

    /**
     * Post {@link LeftClickEvent} at left mouse click.
     *
     * @param callbackInfo unused
     **/
    @Inject(method = "clickMouse", at = @At("RETURN"))
    private void leftClickMouse(CallbackInfo callbackInfo) {
        new LeftClickEvent().post();
    }

    /**
     * Post {@link RightClickEvent} at right mouse click.
     *
     * @param callbackInfo unused
     **/
    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    private void rightClickMouse(CallbackInfo callbackInfo) {
        new RightClickEvent().post();
    }

    /**
     * Post {@link SinglePlayerJoinEvent} when joining single player world.
     *
     * @param folderName      name of folder world file is located in.
     * @param worldName       name of world
     * @param worldSettingsIn settings of world
     * @param callbackInfo    unused
     **/
    @Inject(method = "launchIntegratedServer", at = @At("HEAD"))
    private void joinSinglePlayer(String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo callbackInfo) {
        new SinglePlayerJoinEvent(folderName, worldName, worldSettingsIn).post();
    }

    /**
     * Post {@link LoadWorldEvent} when new world is loaded for player.
     *
     * @param worldClient  world client used.
     * @param callbackInfo unused
     **/
    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;)V", at = @At("HEAD"))
    private void loadWorld(WorldClient worldClient, CallbackInfo callbackInfo) {
        // new LoadWorldEvent(worldClient).post(); // DEBUG: Seeing if this event crashes the game.
    }


}
