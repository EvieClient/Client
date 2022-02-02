

package com.evieclient.mixins.client;

import com.evieclient.events.impl.client.PlaySoundEvent;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public class MixinSoundManager {

    /** Post {@link PlaySoundEvent} when sound is played on client.
     * @param callbackInfo used to cancel event
     * @param sound sound played **/
    @Inject(method = "playSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundHandler;getSound(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/audio/SoundEventAccessorComposite;"), cancellable = true)
    private void playSound(ISound sound, CallbackInfo callbackInfo) {
        PlaySoundEvent playSoundEvent = new PlaySoundEvent(sound);
        playSoundEvent.post();
        if (playSoundEvent.isCanceled()) callbackInfo.cancel();
    }

}
